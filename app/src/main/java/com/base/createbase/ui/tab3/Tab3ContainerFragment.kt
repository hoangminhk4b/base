package com.base.createbase.ui.tab3

import android.view.View
import app.base.common.base.viewbinding.BaseContainerFragment

// File Tab3ContainerFragment
// @project Create Base
// @author minhhoang on 14-01-2021
class Tab3ContainerFragment: BaseContainerFragment() {
    override fun initView(view: View) {
        getFragmentController().switchFragment(Tab3Fragment::class.java,getOption(Tab3Fragment::class.java.simpleName))
    }
}