package com.example.rxjavaexamples;

import android.util.Log;

public class ObserverSecond implements Observer {
    private static String TAG = "ObserverSecond";
    @Override
    public void onSubmit(String msg) {
        Log.d(TAG, "onSubmit: " + msg);
    }
}
