package com.nexus.user.status.ui.views;

public interface OnboardingView {

    /**
     * show user login screen for authentication
     * TODO: This is not imeplemented as of now.
     */
    void openLoginActivity();

    /**
     * Allow user to enter the app after login success
     */
    void openMainActivity();
}
