package app.base.common.base

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.multidex.MultiDex
import app.base.common.R
import app.base.common.helper.LocaleHelper
import app.base.common.helper.SharePreferencesLoader
import app.base.common.helper.Utils
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import java.util.*
import javax.net.ssl.X509TrustManager

// File BaseApplication
// @project Create Base
// @author minhhoang on 07-01-2021
abstract class BaseApplication : Application() {
    private var mObjectTemp: Any? = null
    private var mListObjectTemp: List<*>? = null
    private var mRememberToken: String? = null

    abstract fun baseUrlRetrofit(): String

    companion object {
        private lateinit var sInstance: BaseApplication

        @JvmStatic
        fun getInstance(): BaseApplication {
            return sInstance
        }

        @JvmStatic
        fun checkNetwork(): Int {
            return Utils.checkNetwork(getInstance(), sInstance.getString(R.string.no_internet))
        }
    }


    /*Cẩn thận khi dùng WeakReference, biến phải lưu ở global, k được truyền biến local.
    Nếu không khi next screen sẽ null.
    */
    private var data: MutableMap<String, WeakReference<Any>>? = HashMap()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "vi"))
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        SharePreferencesLoader.getInstance().setupContext(applicationContext).build()
        setupRetrofit()
        init()
    }

    abstract fun init()

    private fun setupRetrofit() {
        val client = setupOkHttpClient()

        val gson = getGsonBuilder()
        val retrofitBuilder = Retrofit.Builder()
            .client(client)
        if (gson != null) retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson))
        retrofitBuilder.baseUrl(
            baseUrlRetrofit()
        )
        createApiManager(retrofitBuilder.build())
    }

    private fun setupOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)
            addInterceptor(requestBuilder)
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val sslSocketFactory = Utils.getSSLSocketFactory()
        sslSocketFactory?.let {
            httpClient.sslSocketFactory(it, Utils.trustAllCerts[0] as X509TrustManager)
        }
        return httpClient.build()
    }

    abstract fun addInterceptor(requestBuilder: Request.Builder)
    abstract fun createApiManager(retrofit: Retrofit)

    open fun getGsonBuilder(): Gson? {
        return null
    }

    fun getAppContext(): Context {
        return sInstance.applicationContext
    }


    // Start: Object temp to pass data between activities
    fun setObjectTemp(obj: Any) {
        mObjectTemp = obj
    }

    fun getObjectTemp(): Any? {
        return mObjectTemp
    }

    fun setData(id: String, `object`: Any) {
        data!![id] = WeakReference(`object`)
        Log.d("SIZE: ", "" + data!!.size)
    }

    fun getData(id: String): Any? {
        if (data != null) {
            val objectWeakReference = data!![id]
            if (objectWeakReference != null) {
                return objectWeakReference.get()
            }
        } else {
            return null
        }
        return null
    }


    fun setListObjectTemp(listObject: List<*>) {
        this.mListObjectTemp = listObject
    }

    fun getListObjectTemp(): List<*>? {
        val listObj = this.mListObjectTemp
        this.mListObjectTemp = null
        return listObj
    }

    //     End: Object temp to pass data between activities
    //
    //     Start: Remember token from server to request
    fun setRememberToken(token: String) {
        this.mRememberToken = token
    }

    fun getRememberToken(): String? {
        return this.mRememberToken
    }
    // End: Remember token from server to request

    fun showToast(data: String?) {
        Toast.makeText(
            getInstance(), data,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun getToast(): Toast {
        val mess = Toast.makeText(getInstance(), "", Toast.LENGTH_SHORT)
        mess.cancel()
        return mess
    }
}