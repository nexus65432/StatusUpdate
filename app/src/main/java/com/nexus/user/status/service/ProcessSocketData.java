package com.nexus.user.status.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.nexus.user.status.datamodel.NewUserObj;
import com.nexus.user.status.datamodel.StatusUpdate;
import com.nexus.user.status.events.NewUserEvent;
import com.nexus.user.status.events.UserStatusChangeEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;


public class ProcessSocketData extends IntentService {

    private static final String TAG = "ProcessSocketData";

    public static final String NEW_MESSAGE = "processdata_new_message";

    private static final String MESSAGE_EVENT = "event";

    private static final String MESSAGE_EVENT_STATUS_CHANGE = "state_change";
    private static final String MESSAGE_EVENT_NEW_USER = "user_new";

    private Gson mGson = new Gson();

    public ProcessSocketData() {
        super("ProcessSocketData");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String rawData = intent.getStringExtra(NEW_MESSAGE);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(rawData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject == null) {
            return;
        }

        String eventType = null;
        try {
            eventType = jsonObject.getString(MESSAGE_EVENT);
        } catch (JSONException e) {
            Log.d(TAG,"Processing error from Intent data : " + e.getMessage());
        }

        if (TextUtils.equals(eventType, MESSAGE_EVENT_STATUS_CHANGE)) {
            processStatusChang(rawData);
        } else if (TextUtils.equals(eventType, MESSAGE_EVENT_NEW_USER)) {
            processNewUser(rawData);
        }
    }

    /**
     * Broadcast for any change in status events
     * @param data raw data received from socket
     */
    private void processStatusChang(String data) {
        StatusUpdate statusUpdate = mGson.fromJson(data, StatusUpdate.class);
        UserStatusChangeEvent userStatusEvent = new UserStatusChangeEvent(statusUpdate);
        EventBus.getDefault().post(userStatusEvent);
    }

    /**
     * Broadcast when new user is added from server
     * @param data raw data received from socket
     */
    private void processNewUser(String data) {
        NewUserObj newUserObj = mGson.fromJson(data, NewUserObj.class);
        NewUserEvent newUserEvent = new NewUserEvent(newUserObj);
        EventBus.getDefault().post(newUserEvent);
    }
}
