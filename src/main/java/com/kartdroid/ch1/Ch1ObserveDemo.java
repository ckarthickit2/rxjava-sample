package com.kartdroid.ch1;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.io.Serializable;

/**
 * http://reactivex.io/documentation/contract.html => The Observable Contract.
 */
public class Ch1ObserveDemo implements Runnable {
    @Override
    public void run() {
        System.out.println("\n=======Ch1ObserveDemo==========");
        Observable<String> just =
                Observable.just("item1", "item2", "item3", "item4").doOnNext( (value)-> {
                    System.out.println("do1: " + value);
                }).doOnNext(value -> {
                    System.out.println("do2: " + value);
                }); //doOnNext is an "operator"
        just.subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError: " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
        System.out.println("=======end==========");
//        Observable something = Observable.fromRunnable(tempRunnable::run);
//        something.subscribe();
//        something.subscribe();
//        something.subscribe();
    }

//    interface SomeSAMInterface {
//        void run();
//    }
//    private static SomeSAMInterface tempRunnable = new SomeSAMInterface() {
//        private int incrementCounter = 0;
//        @Override
//        public void run() {
//            System.out.println("counter = " + incrementCounter++);
//        }
//    };
}


