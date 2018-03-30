package com.nexus.user.status.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    private NetworkUtils() {
    }

    /**
     * When the singleton class is loaded, SingletonNetworkHelper class is not loaded into memory
     * and only when someone calls the getInstance method,
     * this class gets loaded and creates the Singleton class instance.
     */
    private static class SingletonNetworkHelper {
        private static final NetworkUtils INSTANCE = new NetworkUtils();
    }

    /**
     * Initialization on demand
     */
    public static NetworkUtils getInstance() {
        return SingletonNetworkHelper.INSTANCE;
    }

    public boolean isNetworkAvailable(Context context) {

        boolean retVal = false;

        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            retVal = true;
        }

        return retVal;
    }
}
