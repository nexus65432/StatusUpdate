package com.nexus.user.status.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexus.user.status.R;

import java.util.List;

/**
 * Binding adapter to set values to the recyclerview
 */
public class UserListBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.user_icon)
                .error(R.drawable.user_icon)
                .into(imageView);
    }

    @BindingAdapter("bind:username")
    public static void setUserName(TextView usernameView, String username) {
        usernameView.setText(username != null ? username : "");
    }

    @BindingAdapter("bind:userlanguages")
    public static void setUserLanguage(TextView userlangView, List<String> languages) {
        if(languages != null) {
            StringBuilder langs = new StringBuilder();
            int length = languages.size();
            int count = 0;
            for (String language : languages) {
                count++;
                langs.append(language);
                if (length != count) {
                    langs.append(", ");
                }
            }
            userlangView.setText(langs.toString());
        }
    }

    @BindingAdapter("bind:userTags")
    public static void setUserTags(TextView userskillsView, List<String> tags) {
        if (tags != null) {
            StringBuilder usertags = new StringBuilder();
            int length = tags.size();
            int count = 0;
            for (String tag : tags) {
                count++;
                usertags.append(tag);
                if (length != count) {
                    usertags.append(", ");
                }
            }
            userskillsView.setText(usertags.toString());
        }
    }

    @BindingAdapter("bind:userlocation")
    public static void setUserLocation(TextView userLocationView, String location) {
        userLocationView.setText(location != null ? location : "");
    }


    @BindingAdapter("bind:userrole")
    public static void setUserRole(TextView userRoleView, String role) {
        userRoleView.setText(role != null ? role : "");
    }

    @BindingAdapter("bind:userStatus")
    public static void setUserStatus(TextView userStatusView, String status) {
        userStatusView.setText(status != null ? status : "");
    }

}
