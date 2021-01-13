package com.base.createbase

import android.view.View
import app.base.common.base.butterknife.BaseFragment

// File TestFragment
// @project Create Base
// @author minhhoang on 12-01-2021
class TestFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_test
    }

    override fun initView(view: View) {
        setTitle("ok nhé")
    }
}