package com.kartdroid.ch2;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Ch2ObserverAdvancedDemo implements Runnable {


    @Override
    public void run() {
        System.out.println("\n=======Ch2ObserverAdvancedDemo==========");
        observableEmitterDemo();
        observableEmitterWithErrorEmission();
        observableEmitterAfterOnCompleteEmission();
        observableExecutionContextDemo();
        System.out.println("=======end==========");
    }


    private static void observableEmitterDemo() {
        System.out.println("=======observableEmitterDemo==========");
        Observable<String> streamOfString = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("First Item");
                emitter.onNext("Second Item");
                emitter.onNext("Third Item");
                emitter.onComplete();
                //NOTE: The below value is undelivered as onComplete is called before
                emitter.onNext("Undelivered Item");
            }
        });
        streamOfString.subscribe(loggingObserver());
    }


    private static void observableEmitterWithErrorEmission() {
        System.out.println("=======observableEmitterWithErrorEmission==========");
        Observable<String> streamOfString = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onError(new Throwable("Sample error"));
                //when onError is sent, any succeeding calls to onNext or onComplete does nothing.
                //NOTE: The below values are undelivered as onError is called before
                emitter.onNext("New item");
                emitter.onComplete();
            }
        });
        streamOfString.subscribe(loggingObserver());
    }

    private static void observableEmitterAfterOnCompleteEmission() {
        System.out.println("=======observableEmitterAfterOnCompleteEmission==========");
        Observable<String> streamOfString = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onComplete();
                // when an onCompleted or onError is already sent, the observable should not send any more notifications.
                // It is more lenient with onNext,
                emitter.onNext("New item");
                // but it will throw "runtime exception on onError".
                //System.out.println("current_thread:" + Thread.currentThread().getName());
                emitter.onError(new Throwable("Sample error"));
            }
        });
        //Subscription + Emission happens in the same thread context.
        try {
            streamOfString.subscribe(loggingObserver());
//        streamOfString
//                .subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.newThread())
//                .subscribe(loggingObserver());
        } catch (UndeliverableException e) {
            /*
              Error is not propagated here.
              Rather , it's swallowed by
              RxJavaPlugins.setErrorHandler(e -> {
                //e.printStackTrace();
              });
              @see {@link com.kartdroid.App.main();
             */
            e.printStackTrace();
        }
    }

    private static void observableExecutionContextDemo() {
        System.out.println("=======observableExecutionContextDemo==========");
        System.out.println("Assembly Execution Context: " + Thread.currentThread().getName());
        Observable.create(emitter -> {
            emitter.onNext("New Item");
            emitter.onComplete();
        }).doOnSubscribe(disposable -> {
            System.out.println("Subscription execution context: " + Thread.currentThread().getName());
        }).subscribe(item -> {
            System.out.println("Observer execution context: " + Thread.currentThread().getName());
        });
    }

    private static Observer<String> loggingObserver() {
        return new Observer<String>() {
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
        };
    }

}
