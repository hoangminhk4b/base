package app.base.common.base.butterknife;

import android.view.ViewGroup;
import android.view.animation.Animation;

import app.base.common.base.FragmentController;

public abstract class BaseFragmentWithAnimationParent extends BaseFragment {

    protected Boolean isAnimation = true;

    @Override
    public FragmentController getFragmentController() {
        setAnimationWithParentFragment(true);
        return super.getFragmentController();
    }

    @Override
    public void onLeft() {
        setAnimationWithParentFragment(true);
        super.onLeft();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
    }

    /**
     * Animation của children sẽ phụ thuộc vào isAnimation của parent
     *
     * @param transit
     * @param enter
     * @param nextAnim
     * @return
     */
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        getAnimationWithParentFragment();
        if (!isAnimation) {
            Animation disableAnimation = new Animation() {
            };
            disableAnimation.setDuration(0);
            return disableAnimation;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    public void setAnimation(Boolean value) {
        this.isAnimation = value;
    }

    public Boolean getAnimation() {
        return isAnimation;
    }

    public void getAnimationWithParentFragment() {
        if (getParentFragment() != null) {
            isAnimation = ((BaseFragment) getParentFragment()).getAnimation();
        }
    }

    public void setAnimationWithParentFragment(Boolean value) {
        if (getParentFragment() != null) {
            ((BaseFragment) getParentFragment()).setAnimation(value);
        }
    }

}
