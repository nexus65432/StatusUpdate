package com.nexus.user.status.ui.onboarding;


import com.nexus.user.status.ui.views.BaseView;

public interface OnboardingPresenter extends BaseView {

    /**
     * On user successfully authenticated
     */
    void onLoginSuccess();

    /**
     * When user authentication failed
     */
    void onLoginFailed();

    /**
     * Cleanup resources when activity is destroyed
     */
    void onDetach();
}
