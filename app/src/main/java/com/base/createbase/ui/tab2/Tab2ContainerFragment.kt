package com.base.createbase.ui.tab2

import android.view.View
import app.base.common.base.viewbinding.BaseContainerFragment

// File Tab3ContainerFragment
// @project Create Base
// @author minhhoang on 14-01-2021
class Tab2ContainerFragment: BaseContainerFragment() {
    override fun initView(view: View) {
        getFragmentController().switchFragment(Tab2Fragment::class.java,getOption(Tab2Fragment::class.java.simpleName))
    }
}