# Project Setup

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
[mindorks_rxjava_subject]: https://blog.mindorks.com/understanding-rxjava-subject-publish-replay-behavior-and-async-subject-224d663d452f
[observer_pattern]: https://en.wikipedia.org/wiki/Observer_pattern
[gradle_init_java_app]: https://docs.gradle.org/current/userguide/build_init_plugin.html#sec:java_application
[rxjava_basic_flow]: ./art/rxjava-basics-flow.png