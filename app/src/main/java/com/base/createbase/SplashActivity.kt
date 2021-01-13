package com.base.createbase

import android.content.Intent
import android.os.Handler
import android.os.Looper
import app.base.common.base.butterknife.BaseActivity
import com.base.createbase.ui.HomeActivity


// File SplashActivity
// @project Create Base
// @author minhhoang on 07-01-2021
class SplashActivity : BaseActivity() {
    override fun getLayoutID(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }, 300)
    }
}