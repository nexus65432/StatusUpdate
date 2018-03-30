package com.nexus.user.status.events;

import com.nexus.user.status.datamodel.StatusUpdate;

/**
 * Class which carry's User status change events
 */
public class UserStatusChangeEvent {

    private StatusUpdate mStatusUpdate;

    public UserStatusChangeEvent(StatusUpdate statusUpdate) {
        this.mStatusUpdate = statusUpdate;
    }

    public StatusUpdate getStatusUpdate() {
        return mStatusUpdate;
    }
}
