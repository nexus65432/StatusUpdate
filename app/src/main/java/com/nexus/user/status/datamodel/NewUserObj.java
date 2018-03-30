package com.nexus.user.status.datamodel;

import com.google.gson.annotations.SerializedName;


public class NewUserObj {

    @SerializedName("event")
    String event;

    @SerializedName("user")
    UserObj user;

    public String getEvent() {
        return event;
    }

    public UserObj getUser() {
        return user;
    }

}
