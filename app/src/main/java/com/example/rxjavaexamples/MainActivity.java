package com.example.rxjavaexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rxjavaexamples.kotlinmodels.Car;
import com.example.rxjavaexamples.kotlinmodels.KtEventEmitter;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RXJava";
    Thread t;

    DisposableObserver<Integer> disObserver = new DisposableObserver<Integer>() {
        @Override
        public void onNext(Integer integer) {
            Log.d(TAG, "onNext: " + integer);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete: ");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.click_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                emitter.sendMsg("Hello!");
            }
        });


        KtEventEmitter emitter = new KtEventEmitter(new Action() {
            @Override
            public void todo(com.example.rxjavaexamples.Observer obs) {
                obs.onSubmit("Hello");
                obs.onSubmit("Vasya");
                obs.onSubmit("Petys");
            }
        });


        final EditText editText = findViewById(R.id.editText);

        Observable<String> textObservable = new Observable<String>() {
            @Override
            protected void subscribeActual(final Observer<? super String> observer) {
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        observer.onNext(s.toString());
                    }
                });
            }
        };


        Presenter presenter = new Presenter();
        presenter.onTextUpdate(textObservable);



        ObserveFirst obs1 = new ObserveFirst();
//
//
        emitter.subscribe(obs1);





        Car c = new Car("Honda","2013");
        Log.d(TAG, "onCreate: " + c);

        ExecutorService service = Executors.newFixedThreadPool(1);

//        Future<?> future = service.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        final Future<String> future = service.submit(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return "Hello";
//            }
//        });
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Log.d(TAG, "run: " + future.get());
//                } catch (ExecutionException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        testOperators();
//        testOther();
    }

    private void testOther(){
//        Observable<Integer> observable = Observable.just(1);
        final Single<Integer> observable = new Single<Integer>() {
            @Override
            protected void subscribeActual(SingleObserver<? super Integer> observer) {
                observer.onSuccess(123);
            }
        };
        DisposableSingleObserver<Integer> observer = new DisposableSingleObserver<Integer>() {
            @Override
            public void onSuccess(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }
        };

        Completable completable = new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver observer) {
                observer.onComplete();
            }
        };

        Maybe<Integer> maybe = new Maybe<Integer>() {
            @Override
            protected void subscribeActual(MaybeObserver<? super Integer> observer) {
//                observer.onComplete();
                observer.onSuccess(1223);
            }
        };
        observable.subscribe(observer);

    }

    private void testOperators(){
        Observable<Integer> observable = Observable.just(1,2,3,4,5);

        observable = observable
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer %2 == 0;
                    }
                })
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer + 10;
                    }
                });

        observable.subscribe(disObserver);
    }


    private void createObservable(){
//        Observable<Integer> observable = Observable.just(1,2,3,4,5);
        ExecutorService service = Executors.newFixedThreadPool(1);
        final Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 123;
            }
        });
//        Observable<Integer> observable = Observable.fromArray(new Integer[]{1,2,3,4,5});
        Observable<Integer> observable = Observable.fromFuture(future);

        observable.subscribe(disObserver);
    }


    private void testBasicObservable(){


        Observable<String> obs = new Observable<String>() {

            @Override
            protected void subscribeActual(final Observer<? super String> observer) {
//                observer.onNext("Vasya");
//                observer.onNext("Petya");
//                observer.onNext("Sveta");
////                observer.onComplete();
//                observer.onError(new Exception("Error"));
                t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            if(t.isInterrupted()){
                                break;
                            }
                            observer.onNext("Hello");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                break;
                            }
                        }
                    }
                });
                t.start();

                observer.onSubscribe(new Disposable() {
                    @Override
                    public void dispose() {
                        t.interrupt();
                    }

                    @Override
                    public boolean isDisposed() {
                        return t.isInterrupted();
                    }
                });
            }
        };

//        obs.subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG, "onNext: " + s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError: " + e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete: ");
//            }
//        });

        final DisposableObserver<String> observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        obs.subscribe(observer);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                observer.dispose();
            }
        }).start();
    }

}


