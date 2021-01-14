package com.base.createbase.ui.tab1

import android.view.View
import app.base.common.base.viewbinding.BaseContainerFragment

// File Tab3ContainerFragment
// @project Create Base
// @author minhhoang on 14-01-2021
class Tab1ContainerFragment: BaseContainerFragment() {
    override fun initView(view: View) {
        getFragmentController().switchFragment(Tab1Fragment::class.java,getOption(Tab1Fragment::class.java.simpleName))
    }
}