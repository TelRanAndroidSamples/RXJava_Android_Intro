package com.example.rxjavaexamples;

import android.util.Log;

public class ObserveFirst implements Observer{
    private static final String TAG = "ObserverFirst";

    @Override
    public void onSubmit(String msg) {
        Log.d(TAG, "onSubmit: " + msg);
    }
}
