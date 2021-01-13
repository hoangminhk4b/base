package app.base.common.base.butterknife;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import app.base.common.R;
import app.base.common.base.FragmentController;
import app.base.common.helper.LocaleHelper;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayoutID();

    protected abstract void initView();

    private FragmentController fragmentController = new FragmentController(getSupportFragmentManager(), R.id.container);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        ButterKnife.bind(this);
        initView();
    }


    public FragmentController getFragmentController() {
        return fragmentController;
    }

    public void setFragmentController(FragmentController fragmentController) {
        this.fragmentController = fragmentController;
    }

    protected FragmentController.Option getOption(String tag) {
        FragmentController.Option option = new FragmentController.Option.Builder()
                .setTag(tag)
                .useAnimation(false)
                .addBackStack(false)
                .isTransactionReplace(true)
                .getOption();
        return option;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
