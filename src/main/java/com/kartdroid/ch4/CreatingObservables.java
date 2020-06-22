package com.kartdroid.ch4;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class CreatingObservables implements Runnable {
    @Override
    public void run() {
        System.out.println("\n=======Ch4CreatingObservables==========");
        observableCreateDemo();
        observableDeferDemo();
        emptyNeverErrorDemo();
        generatorDemo();
        timerDemo();
        intervalDemo();
        System.out.println("=======end==========");
    }

    private void observableCreateDemo() {
        System.out.println("=======observableCreateDemo==========");
        final String desiredItem = "desired item";
        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("item1");
            emitter.onNext("item2");
            emitter.onNext("item3");
            emitter.onNext(desiredItem);
            emitter.onNext("item4"); //will not be emitted to downstream.
            emitter.onNext("item5"); //will not be emitted to downstream.
        });
        observable.subscribe(new Observer<String>() {
            private WeakReference<Disposable> weakDisposableSource;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
                this.weakDisposableSource = new WeakReference<>(d);
            }

            @Override
            public void onNext(String value) {
                System.out.println("onNext:: " + value);
                if (value.equals(desiredItem)) {
                    Disposable disposableSource = weakDisposableSource.get();
                    if (disposableSource != null) {
                        disposableSource.dispose();
                    }
                    System.out.println("===The source will be disposed now===");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError::" + e);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    /**
     * Defer: do not create the Observable until the observer subscribes,
     * and create a fresh Observable for each observer
     */
    private void observableDeferDemo() {
        System.out.println("=======observableDeferDemo==========");
        System.out.println("before_observable_creation: " + getCurrentTime());
        @NonNull final Observable<CharSequence> deferredObservable = Observable.defer(() -> Observable.create(emitter -> {
            emitter.onNext(getCurrentTime());
        }));
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after_observable_creation: " + getCurrentTime());
        deferredObservable.subscribe(value -> {
            System.out.println("during_emission: " + getCurrentTime());
        });
    }

    private static String getCurrentTime() {
        return "current_time: " + System.currentTimeMillis();
    }


    private void emptyNeverErrorDemo() {
        System.out.println("=======emptyDemo==========");
        Observable<String> empty = Observable.empty();
        empty.subscribe(
                value -> System.out.println("This should never be printed!"),
                error -> System.out.println("Or this!"),
                () -> System.out.println("Done will be printed."));

        System.out.println("=======neverDemo==========");
        Observable<String> never = Observable.never();
        never.subscribe(
                v -> System.out.println("This should never be printed!"),
                error -> System.out.println("Or this!"),
                () -> System.out.println("This neither!"));

        System.out.println("=======errorDemo==========");
        Observable<String> error1 = Observable.error(new IOException());
        error1.subscribe(
                v -> System.out.println("This should never be printed!"),
                e -> System.out.println(e),
                () -> System.out.println("This neither!"));

        System.out.println("=======randomErrorDemo==========");
        //A typical use case is to conditionally map or suppress an exception in a chain utilizing onErrorResumeNext
        Observable<String> observable = Observable.fromCallable(() -> {
            if (Math.random() < 0.5) {
                throw new IOException();
            }
            throw new IllegalArgumentException();
        });

        //If an error occurs in a flow, Replace the error with another observab
        Observable<String> result = observable.onErrorResumeNext(error -> {
            if (error instanceof IllegalArgumentException) {
                return Observable.empty();
            }
            return Observable.error(error);
        });

        for (int i = 0; i < 5; i++) {
            result.subscribe(
                    nextValue -> System.out.println("This should never be printed!"),
                    error -> System.out.println(error),
                    () -> System.out.println("Done"));
        }
    }

    //Creates a cold, synchronous and stateful generator of values.
    private void generatorDemo() {
        System.out.println("=======generatorDemo==========");
        int startValue = 1;
        final int incrementValue = 3;
        Observable<Integer> generatorObservable = Observable.generate(() -> startValue, (/*state*/accumulator, /*Emitter<T>*/emitter) -> {
            int nextValue = accumulator + incrementValue;
            if (nextValue <= 5 * incrementValue) {
                emitter.onNext(nextValue);
            } else {
                emitter.onComplete();
            }
            return nextValue;
        });
        generatorObservable.subscribe(System.out::println);
    }

    /**
     * After the specified time, this reactive source signals a single 0L (then completes for Flowable and Observable).
     */
    private void timerDemo() {
        System.out.println("=======timerDemo==========");
        Observable<Long> fireAfterSometime = Observable.timer(1, TimeUnit.SECONDS);
        fireAfterSometime.blockingSubscribe(value_0 -> System.out.println("Noodles is ready!"));
    }

    /**
     * Periodically generates an infinite, ever increasing numbers (of type Long).
     * The intervalRange variant generates a limited amount of such numbers.
     */
    private void intervalDemo() {
        System.out.println("=======intervalDemo==========");
        Observable<Long> tickTock = Observable.interval(500, TimeUnit.MILLISECONDS);
        tickTock.blockingSubscribe(new Observer<Long>() {
            private WeakReference<Disposable> weakDisposable = null;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                weakDisposable = new WeakReference<>(d);
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                if (aLong % 2 == 0) {
                    System.out.println(aLong + ":: tick");
                } else {
                    System.out.println(aLong + ":: tock");
                }
                if (aLong == 5) {
                    Disposable disposable = weakDisposable.get();
                    if (disposable != null && !disposable.isDisposed()) {
                        disposable.dispose();
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("error: " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

}
