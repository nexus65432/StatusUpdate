package com.nexus.user.status.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nexus.user.status.R;
import com.nexus.user.status.ui.UsersListActivity;
import com.nexus.user.status.ui.BaseActivity;
import com.nexus.user.status.ui.views.OnboardingView;


public class OnboardingActivity extends BaseActivity implements OnboardingView {

    public static final int SPLASH_DISPLAY_INTERVAL = 1000;

    private OnboardingPresenterImpl mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_layout);

        mPresenter = new OnboardingPresenterImpl(this);

        // We are not validating at this time so calling loginSuccess
        mPresenter.onLoginSuccess();
    }

    // TODO: Implement logic to show Login screen
    @Override
    public void openLoginActivity() {
        showMainActivity();
    }

    @Override
    public void openMainActivity() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                showMainActivity();
            }
        }, SPLASH_DISPLAY_INTERVAL);
    }

    private void showMainActivity() {
        Intent intent = UsersListActivity.getStartIntent(OnboardingActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void loading(int resource) {

    }
}
