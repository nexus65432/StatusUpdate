package com.nexus.user.status.datamodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.nexus.user.status.BR;

import java.util.List;


public class UserObj extends BaseObservable {

    @SerializedName("name")
    String userName;

    @SerializedName("avatar")
    String userAvatar;

    @SerializedName("github")
    String userGithub;

    @Bindable
    @SerializedName("role")
    String userRole;

    @SerializedName("gender")
    String userGender;

    @SerializedName("languages")
    List<String> userLanguages;

    @SerializedName("tags")
    List<String> userTags;

    @SerializedName("location")
    String userLocation;

    @Bindable
    String userStatus;

    public String getUserName() {
        return userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public String getUserGithub() {
        return userGithub;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getUserGender() {
        return userGender;
    }

    public List<String> getUserLanguages() {
        return userLanguages;
    }

    public List<String> getUserTags() {
        return userTags;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserRole(String userrole) {
        this.userRole = userrole;
        notifyPropertyChanged(BR.userRole);
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userstatus) {
        this.userStatus = userstatus;
        notifyPropertyChanged(BR.userStatus);
    }
}
