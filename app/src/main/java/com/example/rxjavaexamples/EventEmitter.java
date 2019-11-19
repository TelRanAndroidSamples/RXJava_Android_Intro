package com.example.rxjavaexamples;

import android.util.Log;

import java.util.ArrayList;

public class EventEmitter {
    Action action;

    public EventEmitter(Action action) {
        this.action = action;
    }

    public void subscribe(Observer obs){
        this.action.todo(obs);
    }

}
