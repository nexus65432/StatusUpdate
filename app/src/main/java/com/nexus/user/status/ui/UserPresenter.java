package com.nexus.user.status.ui;

import android.support.annotation.NonNull;

import com.nexus.user.status.datamodel.StatusUpdate;
import com.nexus.user.status.datamodel.UserObj;

import java.util.List;

/**
 * Presenter defines the list of tasks which the Implementer can do
 */
public interface UserPresenter {

    /**
     * Get List of users from server
     */
    void fetchUsers();

    /**
     * Get list of users role from server
     */
    void fetchUsersRole();

    /**
     * Map userroles with user objects
     * @param mUserItems
     */
    void updateUserRoles(@NonNull List<UserObj> mUserItems);

    /**
     * Update the list with status change event
     * @param statusObj
     */
    void updateStatusChange(@NonNull List<UserObj> mUserItems, @NonNull StatusUpdate statusObj);

    /**
     * Add new user to the list after updating user roles
     * @param newUserObj
     */
    void updateNewUser(UserObj newUserObj);

    /**
     * Get ready to exit the application
     */
    void onPrepareToExit();

    /**
     * Cleanup resources when activity is destroyed
     */
    void onDetach();
}
