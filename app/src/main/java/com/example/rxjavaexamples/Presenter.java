package com.example.rxjavaexamples;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

public class Presenter {


    private static final String TAG = "PRESENTER";

    public void onTextUpdate(Observable<String> observable) {
        Disposable disp = observable
                .debounce(5000, TimeUnit.MILLISECONDS)
                .subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.d(TAG, "onNext: " + s);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {

                            }
                        }
                );
//                .subscribe(new DisposableObserver<String>() {
//                    @Override
//                    public void onNext(String s) {
//                        Log.d(TAG, "onNext: " + s);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }
}
