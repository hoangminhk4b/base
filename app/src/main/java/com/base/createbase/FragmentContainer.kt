package com.base.createbase

import android.view.View
import app.base.common.base.butterknife.BaseContainerFragment

// File FragmentContainer
// @project Create Base
// @author minhhoang on 12-01-2021
class FragmentContainer : BaseContainerFragment() {
    override fun initView(view: View) {
        getFragmentController().switchFragment(TestFragment::class.java, getOption("test"))
    }
}