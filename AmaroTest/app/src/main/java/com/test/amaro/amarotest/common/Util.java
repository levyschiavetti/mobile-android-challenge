package com.test.amaro.amarotest.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 *  Utility class responsible for carrying general helper methods
 *
 */
public class Util {

    /**
     * Check whether device has active internet connection
     * @param context - The context used to get Connectivity Manager
     * @return true if device is connected to the internet, false otherwise.
     */
    public static boolean isOnline(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager != null) {
            NetworkInfo ni = manager.getActiveNetworkInfo();
            return ni != null && ni.isConnectedOrConnecting();
        } else {
            return false;
        }
    }
}
