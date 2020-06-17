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

## Setup for JAVA 

- Installing Gradle


```bash
brew install gradle
######################################################################### 100.0%
#==> Downloading https://services.gradle.org/distributions/gradle-6.5-all.zip
#==> Downloading from https://downloads.gradle-dn.com/distributions/gradle-6.5-all.zip
######################################################################### 100.0%
#==> Installing dependencies for gradle: openjdk
#==> Installing gradle dependency: openjdk
#==> Pouring openjdk-13.0.2+8_2.catalina.bottle.tar.gz
#==> Caveats
#For the system Java wrappers to find this JDK, symlink it with
#  sudo ln -sfn /usr/local/opt/openjdk/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk.jdk
#
#openjdk is keg-only, which means it was not symlinked into /usr/local,
#because it shadows the macOS `java` wrapper.
#
#If you need to have openjdk first in your PATH run:
#  echo 'export PATH="/usr/local/opt/openjdk/bin:$PATH"' >> /Users/karthickc/.bash_profile
#
#For compilers to find openjdk you may need to set:
#  export CPPFLAGS="-I/usr/local/opt/openjdk/include"
#
#==> Summary
#🍺  /usr/local/Cellar/openjdk/13.0.2+8_2: 631 files, 314.6MB
#==> Installing gradle
#🍺  /usr/local/Cellar/gradle/6.5: 11,644 files, 249.4MB, built in 24 seconds
#==> Caveats
#==> openjdk
#For the system Java wrappers to find this JDK, symlink it with
#  sudo ln -sfn /usr/local/opt/openjdk/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk.jd

#Karthicks-MBP:rxjava-sample karthickc$ gradle -version
gradle -version
#Welcome to Gradle 6.5!
```

- Setting Up `gradle wrapper`

```bash
#Karthicks-MBP:rxjava-sample karthickc$ gradle wrapper
gradle wrapper
#BUILD SUCCESSFUL in 3s

ls
#Karthicks-MBP:rxjava-sample karthickc$ ls
#gradle		gradlew		gradlew.bat

```

- Using [Gralde Init plugin][gradle_init_java_app] to setup `java-application` workspace.

```bash
#Karthicks-MBP:rxjava-sample karthickc$ ./gradlew init --type java-application --test-framework junit-jupiter
./gradlew init --type java-application --test-framework junit-jupiter
#Downloading https://services.gradle.org/distributions/gradle-6.5-bin.zip
#.........10%..........20%..........30%..........40%.........50%..........60%..........70%..........80%.........90%..........100%
#Starting a Gradle Daemon, 1 incompatible Daemon could not be reused, use --status for details
#
#Select build script DSL:
#  1: Groovy
#  2: Kotlin
#Enter selection (default: Groovy) [1..2] 2
#
#Project name (default: rxjava-sample): 
#
#Source package (default: rxjava.sample): com.kartdroid
#
#
#> Task :init
#Get more help with your project: https://docs.gradle.org/6.5/userguide/tutorial_java_projects.html
#
#BUILD SUCCESSFUL in 3m 58s
```

---
[reactivex_intro]: https://reactivex.io/intro.html
[reactive_streams]: http://www.reactive-streams.org/
[reactive_streams_jvm]: https://github.com/reactive-streams/reactive-streams-jvm
[observer_pattern]: https://en.wikipedia.org/wiki/Observer_pattern
[gradle_init_java_app]: https://docs.gradle.org/current/userguide/build_init_plugin.html#sec:java_application
[rxjava_basic_flow]: ./art/rxjava-basics-flow.png