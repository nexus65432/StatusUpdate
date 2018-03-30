package com.nexus.user.status.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Object received from socket
 */
public class StatusUpdate {

    @SerializedName("event")
    String event;

    @SerializedName("user")
    String user;

    @SerializedName("state")
    String state;

    public String getEvent() {
        return event;
    }

    public String getUser() {
        return user;
    }

    public String getState() {
        return state;
    }
}
