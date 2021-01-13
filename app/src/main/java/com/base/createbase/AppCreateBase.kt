package com.base.createbase

import app.base.common.base.BaseApplication
import app.base.common.constants.Constant
import app.base.common.helper.SharePreferencesLoader
import com.base.createbase.network.ApiManager

// File AppCreateBase
// @project Create Base
// @author minhhoang on 07-01-2021

class AppCreateBase: BaseApplication() {
    private lateinit var apiManager: ApiManager
    override fun baseUrlRetrofit(): String {
        return "http://www.google.com"
    }

    override fun init() {

    }

    override fun addInterceptor(requestBuilder: okhttp3.Request.Builder) {
        Constant.KEY_TOKEN = SharePreferencesLoader.getInstance()
            .getValueFromPreferences(Constant.KEY_SAVE_TOKEN, Constant.KEY_SAVE_TOKEN)
        if(Constant.KEY_TOKEN != Constant.KEY_SAVE_TOKEN){
            requestBuilder.addHeader("Authorization",Constant.KEY_TOKEN)
        }
    }

    override fun createApiManager(retrofit: retrofit2.Retrofit) {
        apiManager =retrofit.create(ApiManager::class.java)
    }

}