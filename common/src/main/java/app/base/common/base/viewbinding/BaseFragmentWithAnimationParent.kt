package app.base.common.base.viewbinding

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.databinding.ViewDataBinding
import app.base.common.base.FragmentController

abstract class BaseFragmentWithAnimationParent : BaseFragment() {
    private var animationParent = true

    override fun getFragmentController(): FragmentController {
        setAnimationWithParentFragment(true)
        return super.getFragmentController()
    }

    override fun onLeft(view: View) {
        setAnimationWithParentFragment(true)
        super.onLeft(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val parent = getBinding<ViewDataBinding>().root.parent as ViewGroup
        parent.removeAllViews()
    }

    /**
     * Animation của children sẽ phụ thuộc vào isAnimation của parent
     *
     * @param transit
     * @param enter
     * @param nextAnim
     * @return
     */
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        animationWithParentFragment
        if (!animationParent) {
            val disableAnimation: Animation = object : Animation() {}
            disableAnimation.duration = 0
            return disableAnimation
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    val animationWithParentFragment: Unit
        get() {
            if (parentFragment != null) {
                animationParent = (parentFragment as BaseFragment?)?.isAnimation!!
            }
        }

    fun setAnimationWithParentFragment(value: Boolean?) {
        if (parentFragment != null) {
            (parentFragment as BaseFragment?)!!.isAnimation = value!!
        }
    }
}