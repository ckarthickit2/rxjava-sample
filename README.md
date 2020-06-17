# RxJAVA Tutorial


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

---
[reactivex_intro]: https://reactivex.io/intro.html
[reactive_streams]: http://www.reactive-streams.org/
[reactive_streams_jvm]: https://github.com/reactive-streams/reactive-streams-jvm
[mindorks_rxjava_subject]: https://blog.mindorks.com/understanding-rxjava-subject-publish-replay-behavior-and-async-subject-224d663d452f
[observer_pattern]: https://en.wikipedia.org/wiki/Observer_pattern
[gradle_init_java_app]: https://docs.gradle.org/current/userguide/build_init_plugin.html#sec:java_application
[rxjava_basic_flow]: ./art/rxjava-basics-flow.png