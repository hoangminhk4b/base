package app.base.common.base.viewbinding

import android.util.Log
import android.view.View
import app.base.common.R
import app.base.common.base.FragmentController

// File BaseContainerFragment
// @project Create Base
// @author minhhoang on 12-01-2021
abstract class BaseContainerFragment: BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_container
    }

    abstract override fun initView(view: View)

    override fun getChildLayoutReplace(): Int {
        return R.id.container
    }

    override fun getOption(tag: String?): FragmentController.Option {
        return FragmentController.Option.Builder()
            .setTag(tag)
            .useAnimation(false)
            .addBackStack(false)
            .isTransactionReplace(true)
            .option
    }

    fun popFragment(): Boolean {
        Log.e("BackStackEntryCount",
            "getChildFragmentManager: " + childFragmentManager.backStackEntryCount)
        var isPop = false
        if (childFragmentManager.backStackEntryCount > 0) {
            isPop = true
            childFragmentManager.popBackStack()
        }
        return isPop
    }
}