package app.base.common.base.butterknife;

import android.util.Log;
import android.view.View;

import app.base.common.R;
import app.base.common.base.FragmentController;

/**
 * create by minh 11/12/2018
 * BaseContainerFragment dùng để tạo ra FragmentManager đầu tiên ( hay cha của các fragment transaction sau) và sử dụng
 * FrameLayout có id là R.id.container
 */
public abstract class BaseContainerFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_container;
    }

    protected abstract void initView(View view);

    @Override
    protected int getChildLayoutReplace() {
        return R.id.container;
    }

    protected FragmentController.Option getOption(String tag){
        FragmentController.Option option = new FragmentController.Option.Builder()
                .setTag(tag)
                .useAnimation(false)
                .addBackStack(false)
                .isTransactionReplace(true)
                .getOption();
        return option;
    }
    public boolean popFragment() {
        Log.e("BackStackEntryCount", "getChildFragmentManager: " + getChildFragmentManager().getBackStackEntryCount());
        boolean isPop = false;
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            isPop = true;
            getChildFragmentManager().popBackStack();
        }
        return isPop;
    }
}
