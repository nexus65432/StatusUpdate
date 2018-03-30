package com.nexus.user.status.events;

import com.nexus.user.status.datamodel.NewUserObj;

/**
 * Class which carry's New User objects
 */
public class NewUserEvent {

    public NewUserObj mNewUserObj;

    public NewUserEvent(NewUserObj mNewUserObj) {
        this.mNewUserObj = mNewUserObj;
    }

    public NewUserObj getNewUserObj() {
        return mNewUserObj;
    }
}
