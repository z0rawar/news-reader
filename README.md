# news-reader:

A simple news reader application using lifecycle aware clean architecure and modularized design.

## Tech Stack:
```
- Kotlin
- Clean Architecture 
- MVVM
- Coroutines
- Modularization
- Architecture Components
- Lifecycle Aware Components
- Repository Pattern
- Offline-first architecture
- Dagger2
- Unit Testing
- Mockito
- Kotlin DSL
```

## Modularization:

The project is designed to demonstrate modular android application design. It consists of three modules:
```
-app 
-core
-news
```

App module is the actual android-application, while the rest are android-libraries.
```
-Depends on both :core and :news modules
-Contains minimal code that acts as a glue to bind the other feature modules
```
Core module is the common module which all other modules depend upon, and it does not depend on other modules itself.
```
-Contains classes which are commonly used, like injection and provider classes, like assisted ViewModel injection
 via factories, generic DataProvider interfaces, etc
-This module should be as lightweight as possible, any change to this module will lead to rebuilding of the project
```

News module is a feature module that displays a list of news articles and is also an android-library.
```
-Depends upon :core module
-Contains all classes to be used by the news feature and concrete implementations for :core interfaces
```

## Dependency Injection:

For modular architecture, DI is critical to ensure all dependencies are satisfied between the individual modules.
In this approach, core cannot depend upon any module, news can depend upon only core but not app module, whereas app can
depend upon both core and news module. 
Hence, the ApplicationComponent in app module acts as the master component, and brings in CoreModule as well as NewsModule,
which in turn can provide any dependencies required by them. AndroidInjector classes from dagger-android lib are used here.
The ApplicationModule only provides the application object which can then be used by other feature modules, and effectively
hides the HeadlinesApplication class from the rest. This is because, although the core and news module can not depend upon
app module (will create a circular dependency), they can still use components which the app module adds to the dependency
graph.


## Highlights:

1. Lifecycle aware:
   - Makes full use of MVVM + Livedata + Lifecycle to ensure that the views are immediately notified of the changes from the       data layer.
   - Handles configuration change nicely - does not recreate any of the instances except views on rotation

2. Offline-first:
   - Built using the repository pattern for data layer ensures that dB is the single source of truth.
   - Each news article once fetched is persisted to the dB, all subsequent calls are made to dB instead of network

3. Unit tested: Showcases unit tests for ViewModel and Livedata

4. Modularized: Minimal build times using scalable, modular android architecture


