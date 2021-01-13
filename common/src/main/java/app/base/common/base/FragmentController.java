package app.base.common.base;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import app.base.common.R;


/**
 * @author AnhTran
 * @since 6/6/17
 */


public class FragmentController {
    public static final int EXISTS_FRAGMENT_IN_BACK_STACK = -1;
    public static final int UNKNOWN_INSTANCE_FRAGMENT = -1;
    private FragmentManager mFragmentManager;
    private int mLayoutContainerId;

    public FragmentController(FragmentManager fragmentManager, @IdRes int layoutContainer) {
        mFragmentManager = fragmentManager;
        mLayoutContainerId = layoutContainer;
    }

    public int switchFragmentWithInstance(Fragment fragmentInstance, @NonNull Option option) {
        if (this.isFragmentCurrentExists(option.getTag())) {
            return EXISTS_FRAGMENT_IN_BACK_STACK;
        }
        boolean isExistsBackStack = mFragmentManager.popBackStackImmediate(option.getTag(), 0);
        if (!isExistsBackStack && !fragmentInstance.isRemoving()) {

            if (fragmentInstance == null) {
                return UNKNOWN_INSTANCE_FRAGMENT;
            }

            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            //Lưu ý: khi kết hợp với viewpager, vì viewpager có animation nên việc dùng animation trong transion sẽ bị lỗi
            if (option.isUseAnimation()) {
                if (option.isPresent()) {
                    transaction.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out, R.anim.push_down_in, R.anim.push_down_out);
                } else {
                    transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out);
                }
            }

            if (option.isTransactionReplace()) {
                transaction.replace(mLayoutContainerId, fragmentInstance,option.getTag());
            } else {
                transaction.add(mLayoutContainerId, fragmentInstance,option.getTag());
            }

            if (option.isAddBackStack()) {
                transaction.addToBackStack(option.getTag());
            }
//            int res = transaction.commit();
            int res = transaction.commitAllowingStateLoss();
            return res;
        }
        return EXISTS_FRAGMENT_IN_BACK_STACK;
    }

    public int switchFragment(@NonNull Class<? extends Fragment> fragment, Option option) {
        Fragment fragmentInstance = null;
        try {
            fragmentInstance = fragment.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (fragmentInstance == null) {
            return UNKNOWN_INSTANCE_FRAGMENT;
        }
        return switchFragmentWithInstance(fragmentInstance, option);
    }

    public Fragment getInstanceFragment(Class<? extends Fragment> fragment) {
        Fragment fragmentInstance = null;
        try {
            fragmentInstance = fragment.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (fragmentInstance == null) {
            return null;
        }
        return fragmentInstance;
    }

    public boolean isFragmentCurrentExists(String tag) {
        boolean ret = false;
        int countFragmentInBackStack = mFragmentManager.getBackStackEntryCount();

        if (countFragmentInBackStack > 0) {
            FragmentManager.BackStackEntry currentEntry = mFragmentManager.getBackStackEntryAt(countFragmentInBackStack - 1);
            if (currentEntry != null && currentEntry.getName() != null && currentEntry.getName().equals(tag)) {
                ret = true;
            }
        }
        return ret;
    }

    public Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(mLayoutContainerId);
    }

    public void setLayoutContainerId(int id) {
        this.mLayoutContainerId = id;
    }

    public static class Option {
        private boolean useAnimation = true;
        private boolean isAddBackStack = true;
        private boolean isTransactionReplace = false;

        private boolean isPresent = false;

        private String tag;

        private Option() {
        }

        public boolean isUseAnimation() {
            return useAnimation;
        }

        public boolean isAddBackStack() {
            return isAddBackStack;
        }

        public boolean isPresent() {
            return isPresent;
        }

        public void setPresent(boolean present) {
            isPresent = present;
        }


        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public boolean isTransactionReplace() {
            return isTransactionReplace;
        }

        public static class Builder {
            private final Option option;

            public Builder() {
                this.option = new Option();
            }

            public Builder setTag(String tag) {
                this.option.tag = tag;
                return this;
            }

            public Builder useAnimation(boolean use) {
                this.option.useAnimation = use;
                return this;
            }

            public Builder addBackStack(boolean addBackStack) {
                this.option.isAddBackStack = addBackStack;
                return this;
            }

            public Builder isTransactionReplace(boolean isTransactionReplace) {
                this.option.isTransactionReplace = isTransactionReplace;
                return this;
            }

            public Builder isPresent(boolean isPresent) {
                this.option.isPresent = isPresent;
                return this;
            }

            public Option getOption() {
                return this.option;
            }
        }
    }

}
