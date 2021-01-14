package com.base.createbase.ui.tab1

import android.util.Log
import android.view.View
import app.base.common.base.viewbinding.BaseFragment
import com.base.createbase.R
import com.base.createbase.databinding.FragmentTab1Binding
import com.base.createbase.ui.detail.DetailFragment

// File Tab1Fragment
// @project Create Base
// @author minhhoang on 14-01-2021
class Tab1Fragment: BaseFragment() {
    init {
        Log.d("TAG", "Vào đây rồi nhé: ")
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_tab1
    }

    override fun initView(view: View) {
        getBinding<FragmentTab1Binding>().mGoTo.setOnClickListener {
            getFragmentController().switchFragment(DetailFragment::class.java,getOption(DetailFragment::class.java.simpleName))
        }
    }
}