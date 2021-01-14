package com.base.createbase.ui

import android.content.Intent
import app.base.common.base.viewbinding.BaseActivity
import com.base.createbase.FragmentContainer
import com.base.createbase.R
import com.base.createbase.databinding.ActivityMainBinding

// File HomeActivity
// @project Create Base
// @author minhhoang on 13-01-2021

class HomeActivity : BaseActivity() {
    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        getBinding<ActivityMainBinding>().mNavigation.setOnClickListener {
            startActivity(Intent(this, NavigationActivity::class.java))
        }
        getBinding<ActivityMainBinding>().mTabHost.setOnClickListener {
            startActivity(Intent(this, TabHostActivity::class.java))
        }
        getBinding<ActivityMainBinding>().mViewPage.setOnClickListener {
            startActivity(Intent(this, ViewPageActivity::class.java))
        }
    }
}