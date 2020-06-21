package com.kartdroid.ch5;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.util.ArrayListSupplier;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TransformingObservables implements Runnable {
    @Override
    public void run() {
        System.out.println("\n=======TransformingObservables==========");
        bufferDemo();
        mapDemo();
        castDemo();
        concatMapDemo();
        flatMapDemo();
        System.out.println("=======end==========");
    }

    //Collects the items emitted by a reactive source into buffers, and emits these buffers.
    private void bufferDemo() {
        System.out.println("=======bufferDemo==========");
        Disposable disposable = Observable.range(0, 10) // creational opeartor
                .buffer(4, 4, ArrayListSupplier.asSupplier())
                .subscribe((List<Integer> bufferedValue) -> {
                    System.out.println("fromBuffer: " + bufferedValue);
                });
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }


    /**
     * Applies the given {@link io.reactivex.rxjava3.functions.Function} to each item emitted by a reactive source and emits the results of these function applications.
     */
    private void mapDemo() {
        System.out.println("=======mapDemo==========");
        Observable.just(1, 2, 3)
                .map(x -> x * x)
                .subscribe(System.out::println);
        // prints:
        // 1
        // 4
        // 9
    }


    //Converts each item emitted by a reactive source to the specified type, and emits these items.
    private void castDemo() {
        System.out.println("=======castDemo==========");
        Observable<Number> numbers = Observable.just(1, 4.0, 3f, 7, 12, 4.6, 5);
        numbers.filter(value -> Integer.class.isInstance(value))
                .cast(Integer.class)
                .subscribe(
                        (Integer value) -> {
                            System.out.print(value + ",\t");
                        },
                        Functions.ERROR_CONSUMER,
                        () -> System.out.println(""));

    }


    /**
     * Applies the given {@link io.reactivex.rxjava3.functions.Function} to each item emitted by a reactive source,
     * where that function returns a reactive source,
     * and emits the items that "result from concatenating the results" of these function applications.
     * <p>
     * "mapper" function is executed for only 1 item at any time and hence the "resultant Observable"s are subscribed one by one (internally).
     * <p>
     * Eg., If the mapper function is applied for 1st item and the resultant Observable takes time to emit, then the remaining values are just queued up.
     * Once the 1st Observable completes, then the mapper function for 2nd item is applied and the resultant Observable is subscribed.
     * This process is repeated.
     */
    private void concatMapDemo() {
        System.out.println("=======concatMapDemo==========");
        Observable.range(0, 5)
                .concatMap(value -> {
                    long delay = Math.round(Math.random() * 500);
                    return Observable.timer(delay, TimeUnit.MILLISECONDS).map(timerCounter -> value);
                }).blockingSubscribe((Integer value) -> System.out.print("" + value), Functions.ERROR_CONSUMER, System.out::println);
    }

    /**
     * Applies the given {@link io.reactivex.rxjava3.functions.Function}  to each item emitted by a reactive source,
     * where that function returns a reactive source,
     * and emits the items that "result from merging the results" of these function applications.
     */
    private void flatMapDemo() {
        System.out.println("=======flatMapDemo==========");
        Observable.just("A", "B", "C").flatMap((upstreamValue) -> {
            return Observable.intervalRange(/*start*/1L,/*count*/3L, /*initialDelay*/0L, /*period*/1, TimeUnit.SECONDS)
                    .map(mappedValue -> upstreamValue + ": " + mappedValue);
        }).blockingSubscribe(System.out::println);
    }
}
