package com.nexus.user.status.network;

import android.util.Log;

import com.nexus.user.status.events.SocketEvents;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Listener to connect to socket for handling messages
 */
public class StatusWebSocketListener extends WebSocketListener {

    private static final String TAG = "StatusWSListener";

    public static final int NORMAL_CLOSURE_STATUS = 1000;

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d(TAG,"Receiving : " + text);

        if (text != null) {
            // Which ever UI want's to process will listen to this data and handle it.
            EventBus.getDefault().post(new SocketEvents(text));
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.d(TAG,"Receiving bytes : " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Log.d(TAG,"Closing : " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.d(TAG,"Error : " + t.getMessage());
    }

}
