package com.base.createbase

import app.base.common.base.butterknife.BaseActivity


// File SplashActivity
// @project Create Base
// @author minhhoang on 07-01-2021
class SplashActivity : BaseActivity() {
    override fun getLayoutID(): Int {
        return R.layout.activity_container
    }

    override fun initView() {
        fragmentController.switchFragment(FragmentContainer::class.java, getOption("ok"))
    }
}