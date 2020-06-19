package com.kartdroid.ch3;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Operators accept input, manipulate it and  produce an output.
 */
public class Ch3OperatorsDemo implements Runnable {
    @Override
    public void run() {
        System.out.println("\n=======Ch3OperatorsDemo==========");
        observableCreationOperatorsDemo();
        mapOperatorDemo();
        System.out.println("=======end==========");
    }

    private void observableCreationOperatorsDemo() {
        System.out.println("=======observableCreationOperatorsDemo==========");
        Observable.interval(1, TimeUnit.SECONDS)  //The Operator
                .take(5)  //Take Operator takes only 1st 5 from upstream
                .blockingForEach(System.out::println);
    }
    private void mapOperatorDemo() {
        System.out.println("=======mapOperatorDemo==========");
        Observable.range(1,10)
                .map( value -> String.format("%d -> %d",value , value * 10))  //The Operator
                .subscribe(System.out::println);
    }
}
