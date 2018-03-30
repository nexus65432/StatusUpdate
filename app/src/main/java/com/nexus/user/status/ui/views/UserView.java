package com.nexus.user.status.ui.views;

import android.support.annotation.NonNull;

import com.nexus.user.status.datamodel.UserObj;

import java.util.List;


public interface UserView extends BaseView {

    /**
     * Pass the data to UI to update the list
     * @param results
     */
    void updateUserList(@NonNull List<UserObj> results);

    /**
     * Trigger to request user roles mapping from server.
     */
    void fetchUserRoles();

    /**
     * Update the user roles mapping for the current user list
     */
    void updateUserRoles();

    /**
     * Add new user received from server to the list
     * @param newUser
     */
    void updateSingleUser(@NonNull UserObj newUser);

}
