package com.base.createbase.ui

import app.base.common.base.viewbinding.BaseActivity
import com.base.createbase.FragmentContainer
import com.base.createbase.R

// File HomeActivity
// @project Create Base
// @author minhhoang on 13-01-2021

class HomeActivity: BaseActivity() {
    override fun initView() {
        getFragmentController().switchFragment(FragmentContainer::class.java,getOption("ok"))
    }
}