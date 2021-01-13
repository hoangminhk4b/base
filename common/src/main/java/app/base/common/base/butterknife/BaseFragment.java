package app.base.common.base.butterknife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import app.base.common.R;
import app.base.common.R2;
import app.base.common.base.FragmentController;
import app.base.common.helper.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @BindView(R2.id.btLeft)
    ImageView btLeft;

    @Nullable
    @BindView(R2.id.btRight)
    ImageView btRight;

    @Nullable
    @BindView(R2.id.tvTitleHeader)
    TextView tvTitleHeader;

    @Nullable
    @BindView(R2.id.btRight1)
    ImageView btRight1;

    protected View view;
    protected FragmentController controllerFragment;
    //    protected CustomToast mess;
    protected Boolean isAnimation = true;


    protected abstract int getLayoutId();

    protected abstract void initView(View view);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, view);
            setLeftImage(R.drawable.ic_back_left);
            setColorTitle(R.color.text_black);
            Utils.hideKeyword(view, getActivity());
            initFragmentController();
            initView(view);
        }
        //Override hàm này khi muốn reload lại data
        reloadData();
        return view;
    }

    private void initFragmentController() {
        if (getChildLayoutReplace() != 0) {
            controllerFragment = new FragmentController(getChildFragmentManager(), getChildLayoutReplace());
        } else {
            controllerFragment = new FragmentController(getParentFragmentManager(), R.id.container);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Disable Touch Xuyên xuống màn hình dưới khi add fragment
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    protected void setTitle(String title) {
        if (tvTitleHeader != null) {
            tvTitleHeader.setText(title);
        }
    }

    protected void setColorTitle(int color) {
        if (tvTitleHeader != null) {
            tvTitleHeader.setTextColor(getResources().getColor(color));
        }
    }

    protected void setLeftImage(int img) {
        if (btLeft != null) {
            btLeft.setImageResource(img);
        }
    }

    protected void setRightImage(int img) {
        if (btRight != null) {
            btRight.setImageResource(img);
        }
    }

    protected void setRight1Image(int img) {
        if (btRight1 != null) {
            btRight1.setImageResource(img);
        }
    }

    //Set trạng thái ẩn hiện nút left: GONE, VISIBLE, INVISIBLE
    protected void setStateLeft(int state) {
        if (btLeft != null) {
            btLeft.setVisibility(state);
        }
    }

    //Set trạng thái ẩn hiện nút right
    protected void setStateRight(int state) {
        if (btRight != null) {
            btRight.setVisibility(state);
        }
    }

    //Set trạng thái ẩn hiện nút right1
    protected void setStateRight1(int state) {
        if (btRight1 != null) {
            btRight1.setVisibility(state);
        }
    }

    protected void reloadData() {

    }

    protected FragmentController.Option getOption(String tag) {
        return new FragmentController.Option.Builder()
                .setTag(tag)
                .useAnimation(true)
                .addBackStack(true)
                .isTransactionReplace(true)
                .getOption();
    }


    public FragmentController getFragmentController() {
        return controllerFragment;
//        return getBaseActivity().getFragmentController();
    }

    protected int getChildLayoutReplace() {
        return 0;
    }

    @Optional
    @OnClick(R2.id.btLeft)
    public void onLeft() {
        if (getParentFragmentManager().getBackStackEntryCount() > 0) {
            getParentFragmentManager().popBackStack();
        } else {
            getActivity().onBackPressed();
        }
    }

    @Optional
    @OnClick(R2.id.btRight)
    public void onRight() {
    }

    @Optional
    @OnClick(R2.id.btRight1)
    public void onRight1() {
    }

    public void setAnimation(Boolean value) {
        this.isAnimation = value;
    }

    public Boolean getAnimation() {
        return isAnimation;
    }
}
