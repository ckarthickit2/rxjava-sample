/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.kartdroid;

import com.kartdroid.ch1.Ch1ObserveDemo;
import com.kartdroid.ch2.Ch2ObserverAdvancedDemo;
import com.kartdroid.ch3.Ch3OperatorsDemo;
import com.kartdroid.ch4.Ch4CreatingObservables;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

public class App {
    public Flowable<String> getGreetingFlow() {
        return Flowable.just("Hello world.", "We are gonna learn RxJava");
    }

    public static void main(String[] args) {
        RxJavaPlugins.setErrorHandler(e -> {
            System.out.println("Uncaught Exception : " + e);
        });
        RxJavaPlugins.lockdown();
        new App().getGreetingFlow().subscribe(System.out::println).dispose();
        Observable.fromArray(
                new Ch1ObserveDemo(),
                new Ch2ObserverAdvancedDemo(),
                new Ch3OperatorsDemo(),
                new Ch4CreatingObservables()
        ).doOnNext(Runnable::run).subscribe();
    }
}
