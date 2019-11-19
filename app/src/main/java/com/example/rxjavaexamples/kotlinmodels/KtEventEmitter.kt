package com.example.rxjavaexamples.kotlinmodels

import com.example.rxjavaexamples.Action
import com.example.rxjavaexamples.Observer

class KtEventEmitter(var  action: Action) {
    fun subscribe(obs: Observer){
        action.todo(obs)
    }
}