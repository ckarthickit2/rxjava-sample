# RxJAVA Tutorial

![RxJava Sample Build Status](https://github.com/ckarthickit2/rxjava-sample/workflows/rxjava-sample-workflow/badge.svg)

## What is ReactiveX 

- [ReactiveX][reactivex_intro] is a library for *"composing"* **asynchronous** and **event-based** programs using `Observable` *sequences*.
- Extends the [Observer Pattern][observer_pattern].  
  Observables fill the gap by being the ideal way to **access asynchronous sequences of multiple items**.

  |              |      single item        |    multiple items            |
  |      ---     |         ---              |        ----                 |
  | synchronous  |   `T getData()`          |   `Iterable<T> getData()`   |
  | asynchronous |   `Future<T> getData()`  |   `Observable<T> getData()` |

- [Reactive Streams][reactive_streams]  is a standard for asynchronous stream processing with non-blocking back pressure.
  
### Basic Building Blocks

- **Observables**      - Emits `data-streams`.
- **Observer** - Consumes `data-streams`
  - `Subscriber` has the methods to **unsubscribe/resubscribe** independently without the need of the observable methods.
- **Operators** - Transform `data-streams`.
 
    ![RXJava Basic Building Blocks][rxjava_basic_flow]

- **Subject** - Represents an `Observable` and `Observer` at the same time
    - allows multi-casting events from single source to multiple child.
  > A Subject is a sort of bridge or proxy that is available in some implementations of ReactiveX
  > that acts both as an observer and as an Observable.
  > Because it is an observer, it can subscribe to one or more Observables,
  > and because it is an Observable, it can pass through the items it observes by re-emitting them, and it can also emit new items.    

### Reactive Streams Counterparts

- **Publisher&lt;T&gt;** - a provider of a potentially unbounded number of sequenced elements
- **Subscriber&lt;T&gt;** - Consumes `data-streams`.
- **Processor&lt;T,R&gt;** - Represents Processing Stage which is both a `Publisher` and `Subscriber`.
- **Subscription** - represents a one-to-one lifecycle of a `Subscriber` subscribing to a `Publisher`.
  - Can only be used once by a **single Subscriber**.
  - Used to both signal desire for data and cancel demand.

### Java9 Reactive Stream Counterparts (java.util.concurrent.Flow) 

- **Flow.Publisher&lt;T&gt;** - A producer of items (and related control messages) received by Subscribers.
- **Flow.Subscriber&lt;T&gt;** - A receiver of messages.
- **Flow.Processor&lt;T,R&gt;** - A component that acts as both a Subscriber and Publisher.
- **Flow.Subscription** - Message control linking a Flow.Publisher and Flow.Subscriber.

> The interfaces available in JDK9’s java.util.concurrent.Flow,
> are 1:1 semantically equivalent to their respective Reactive Streams counterparts.

### RXJava Base Publishers

> Note that RxJava 3 components now live under `io.reactivex.rxjava3` and the base classes and interfaces live under `io.reactivex.rxjava3.core`.

RxJava 3 features several base classes you can discover operators on:

- [`io.reactivex.rxjava3.core.ObservableSource`](http://reactivex.io/RxJava/3.x/javadoc/io/reactivex/rxjava3/core/ObservableSource.html): Represents a basic, non-backpressured [Observable][rxjava_obervable] **source** base interface, consumable via an Observer.
- [`io.reactivex.rxjava3.core.Flowable`](http://reactivex.io/RxJava/3.x/javadoc/io/reactivex/rxjava3/core/Flowable.html): 0..N flows, **supporting Reactive-Streams and backpressure**
- [`io.reactivex.rxjava3.core.Observable`][rxjava_obervable]: 0..N flows, **no backpressure**,
- [`io.reactivex.rxjava3.core.Single`](http://reactivex.io/RxJava/3.x/javadoc/io/reactivex/rxjava3/core/Single.html): a flow of exactly 1 item or an error,
- [`io.reactivex.rxjava3.core.Completable`](http://reactivex.io/RxJava/3.x/javadoc/io/reactivex/rxjava3/core/Completable.html): a flow without items but only a completion or error signal,
- [`io.reactivex.rxjava3.core.Maybe`](http://reactivex.io/RxJava/3.x/javadoc/io/reactivex/rxjava3/core/Maybe.html): a flow with no items, exactly one item or an error.

>To avoid name clash, the RxJava 1 `rx.Subscription` has been renamed into `io.reactivex.Disposable` in RxJava 2.


## Terminologies

- **Assembly Time** 
    - Involves **preparation of data-flows** by applying various intermediate **operators**.
    - Data is not flowing yet.
    - No "side effects" are happening.
    
      ```javascript
          Flowable<Integer> flow = Flowable.range(1, 5)
            .map(v -> v * v)
            .filter(v -> v % 3 == 0)
            ;
      ```
- **Subscription Time**
  - temporary state when `subscribe()` is called on an Observable.
  - chain of processing steps are established internally.
  - **subscription "side effects"** are triggered (`doOnSubscribe`)
  
      ```javascript
          flow.subscribe(System.out::println)
      ```
- **Runtime**
  - source is actually emitting items.
  
      ```javascript
          Observable.create(emitter -> {
               while (!emitter.isDisposed()) {
                   long time = System.currentTimeMillis();
                   emitter.onNext(time);
                   if (time % 2 != 0) {
                       emitter.onError(new IllegalStateException("Odd millisecond!"));
                       break;
                   }
               }
          })
          .subscribe(System.out::println, Throwable::printStackTrace);
      ```
      > The body of the above example executes at this stage.
- **Data Flow** 
    Consists of **a source** , zero or more **intermediate steps**, followed by a data consumer (or) combinator.
  - `Upstream` -  Looking to the left/up towards the source is called upstream.
  - `Downstream` -  Looking to the right/down towards the consumer is called downstream.

- **Parallel Processing** 
  - Practically, parallelism in RxJava means **running independent flows** and **merging their results back into a single flow**.
  
## Operators by Category  

1. **Creating Observables** - *Operators that originate new Observables.*
    - Create, Defer, Empty / Never / Throw, From, Interval, Just etc.,
2. **Transforming Observables** - *Operators that transform items that are emitted by an Observable.*
    - Buffer, FlatMap, GroupBy, Map, Scan, Window.
3. **Filtering Observables** - *Operators that transform items that are emitted by an Observable.*
    - Debounce , Distinct, ElementAt, Filter, First, Last, Skip, SkipLast, Take, TakeLast, IgnoreElements, Sample.
4. **Combining Observables** - *Operators that work with multiple source Observables to create a single Observable.*
    - And / Then / When, Join, Merge, Switch, Zip
5. **Error Handling Operators**  - *Operators that help to recover from error notifications from an Observable.*
    - Catch, Retry
6. **Observable Utility Operators** - *A toolbox of useful Operators for working with Observables.*
    - Delay, TimeInterval, Timeout, Timestamp, Subscribe, SubscribeOn, ObserveOn etc., 
7. **Conditional and Boolean Operators** - *Operators that evaluate one or more Observables or items emitted by Observables.*
    - All, Amb, Contains, DefaultIfEmpty, SkipUntil, SkipWhile, TakeUntil, TakeWhile
8. **Mathematical and Aggregate Operators** - *Operators that operate on the entire sequence of items emitted by an Observable.*
    - Average, Concat, Count, Max, Min, Reduce, Sum
9. **Backpressure Operators** - *strategies for coping with Observables that produce items more rapidly than their observers consume them.*
    - `Flowable`
10. **Connectable Observable Operators** - *Specialty Observables that have more precisely-controlled subscription dynamics.*
    - Connect, Publish, RefCount, Replay
11. **Operators to convert Observables** - *convert an Observable into another object or data structure.*
    - `To` 
 
### 

  
## Cold vs Hot Publishers

|         Cold Publishers                                |                         Hot Publishers                                           |
|        -----------------                               |                        -----------------                                         |
| They only begin emitting when  there is a subscriber   |   They begins generating items and emits them immediately when they are created  |
| All subscribers receive the exact set of historical data, regardless of when they subscribe |       Reception of data depends on the type of Publisher (Eg.,`Subject`) used |
| The subscriber is taking elements only when it is ready to process that item (**in a pull fashion**) | They emits items at its own pace, and it is up to its subscriber to keep up. (**push fashion**)|
| Items do not need to be buffered in a publisher because they are requested in pull fashion | If subscriber is not able to consume items as quickly as they are produced by a publisher, they need to be buffered or handled in some other way, as they will fill up the memory, finally causing OutOfMemoryException |
| **data-driven** is cold                                | **event-driven** is hot                                                            |
| **Flowable** and **Observable** are generally cold     | **Subjects** are one way to handle hot observables                               |  

## References

- Anatomy of RxJava is explained @[Mindorks RxJava Anatomy Blog][mindorks_rxjava_anatomy]
- [Intro to RXJava][froussios_intro_to_rxjava] by Froussios 
- [Rxjava Ninja][rxjava_ninja] by Tompee
- [ReactiveX Contract][reactivex_contract]
- [ReactiveX Operators][reactivex_operators]
- [RXWorkshop: Introduction][rx_workshop_intro]
- [Stackoverflow: Observable vs Flowable][observable_vs_flowable]
- [RxJava Wiki on Creational Operators][rxjava_wiki_operators_create]
---
[reactivex_intro]: https://reactivex.io/intro.html
[reactivex_contract]: http://reactivex.io/documentation/contract.html
[reactivex_operators]: http://reactivex.io/documentation/operators.html
[reactive_streams]: http://www.reactive-streams.org/
[reactive_streams_jvm]: https://github.com/reactive-streams/reactive-streams-jvm
[mindorks_rxjava_anatomy]: https://blog.mindorks.com/rxjava-anatomy-what-is-rxjava-how-rxjava-is-designed-and-how-rxjava-works-d357b3aca586
[mindorks_rxjava_subject]: https://blog.mindorks.com/understanding-rxjava-subject-publish-replay-behavior-and-async-subject-224d663d452f
[observer_pattern]: https://en.wikipedia.org/wiki/Observer_pattern
[gradle_init_java_app]: https://docs.gradle.org/current/userguide/build_init_plugin.html#sec:java_application
[rxjava_basic_flow]: ./art/rxjava-basics-flow.png
[rxjava_obervable]: http://reactivex.io/RxJava/3.x/javadoc/io/reactivex/rxjava3/core/Observable.html
[rxjava_wiki_operators_create]: https://github.com/ReactiveX/RxJava/wiki/Creating-Observables
[froussios_intro_to_rxjava]: https://github.com/Froussios/Intro-To-RxJava
[rxjava_ninja]: https://medium.com/tompee/rxjava-ninja-introduction-to-reactive-programming-4b1e27b20576
[rx_workshop_intro]: https://channel9.msdn.com/Series/Rx-Workshop/Rx-Workshop-Introduction
[observable_vs_flowable]: https://stackoverflow.com/questions/40323307/observable-vs-flowable-rxjava2