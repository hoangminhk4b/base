package app.base.common.base.viewbinding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import app.base.common.R
import app.base.common.base.FragmentController
import app.base.common.databinding.HeaderLayoutBinding
import app.base.common.helper.Utils

// File BaseFragment
// @project Create Base
// @author minhhoang on 12-01-2021
abstract class BaseFragment : Fragment() {
    lateinit var controllerFragment: FragmentController
    open var isAnimation = true
    private lateinit var binding: ViewDataBinding
    private var headerBinding: HeaderLayoutBinding? = null

    fun <T : ViewDataBinding> getBinding() = binding as T

    fun getHeaderBinding() = headerBinding

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView(view: View)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d("onCreateView", "onCreateView: run here")
        if (!this::binding.isInitialized) {
            binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
            headerBinding = HeaderLayoutBinding.bind(binding.root)
            Utils.hideKeyword(binding.root, getBaseActivity())
            initFragmentController()
            initView(binding.root)
        }
        //Override hàm này khi muốn reload lại data
        reloadData()
        return binding.root
    }

    private fun initFragmentController() {
        controllerFragment = if (getChildLayoutReplace() != 0) {
            FragmentController(childFragmentManager, getChildLayoutReplace())
        } else {
            FragmentController(parentFragmentManager, R.id.container)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Disable Touch Xuyên xuống màn hình dưới khi add fragment
        view.setOnTouchListener { _, _ -> true }
    }

    protected fun reloadData() {}

    protected open fun getOption(tag: String?): FragmentController.Option? {
        return FragmentController.Option.Builder()
            .setTag(tag)
            .useAnimation(true)
            .addBackStack(true)
            .isTransactionReplace(true)
            .option
    }

    protected open fun getBaseActivity(): BaseActivity {
        return getActivity() as BaseActivity
    }


    open fun getFragmentController(): FragmentController {
        return controllerFragment
//        return getBaseActivity().getFragmentController();
    }

    protected open fun getChildLayoutReplace(): Int {
        return 0
    }

    fun onLeft() {
        if (parentFragmentManager.backStackEntryCount > 0) {
            parentFragmentManager.popBackStack()
        } else {
            getBaseActivity().onBackPressed()
        }
    }

    fun onRight() {
    }

    fun onRight1() {
    }
}