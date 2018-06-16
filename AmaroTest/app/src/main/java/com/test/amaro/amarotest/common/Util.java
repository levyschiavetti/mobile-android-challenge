package com.test.amaro.amarotest.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {

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
