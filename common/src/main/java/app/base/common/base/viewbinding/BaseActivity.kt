package app.base.common.base.viewbinding

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import app.base.common.R
import app.base.common.base.FragmentController
import app.base.common.helper.LocaleHelper

// File BaseActivity
// @project Create Base
// @author minhhoang on 12-01-2021
abstract class BaseActivity: AppCompatActivity() {
    protected open fun getLayoutID(): Int {
        return R.layout.activity_container
    }

    private lateinit var binding: ViewDataBinding

    fun <T: ViewDataBinding> getBinding() = binding as T

    protected abstract fun initView()

    private var fragmentController = FragmentController(
        supportFragmentManager, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,getLayoutID())
        initView()
    }


    open fun getFragmentController(): FragmentController {
        return fragmentController
    }

    open fun setFragmentController(fragmentController: FragmentController) {
        this.fragmentController = fragmentController
    }

    protected open fun getOption(tag: String?): FragmentController.Option {
        return FragmentController.Option.Builder()
            .setTag(tag)
            .useAnimation(false)
            .addBackStack(false)
            .isTransactionReplace(true)
            .option
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base!!))
    }
}