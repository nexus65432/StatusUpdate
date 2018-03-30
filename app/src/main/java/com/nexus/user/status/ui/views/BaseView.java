package com.nexus.user.status.ui.views;


import android.support.annotation.StringRes;

public interface BaseView {

    /**
     * Show progress update to UI
     * @param resource
     */
    void loading(@StringRes int resource);

    /**
     * Update UI in case of error with resource
     * @param resource
     */
    void onError(@StringRes int resource);
}
