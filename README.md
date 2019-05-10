# news-reader:

A simple news reader application using lifecycle aware clean architecure and modularized design.

##Tech Stack:
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

##Modularization:

The project is designed to demonstrate modular android application design. It contains of three modules:
```
-app 
-core
-news
```

App module is the actual android-application, while the rest are android-libraries.
-Depends on both :core and :news modules
-Contains minimal code that acts as a glue to bind the other feature modules

Core module is the common module which all other modules depend upon, and it does not depend on other modules itself.
-Contains classes which are commonly used, like injection and provider classes, like assisted ViewModel injection
 via factories, generic DataProvider interfaces, etc
-This module should be as lightweight as possible, any change to this module will lead to rebuilding of the project

News module is a feature module that displays a list of news articles and is also an android-library.
-Depends upon :core module
-Contains all classes to be used by the news feature and concrete implementations for :core interfaces


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
-Makes full use of MVVM + Livedata + Lifecycle to ensure that the views are immediately notified of the changes from the data layer.
-Handles configuration change nicely - does not recreate any of the instances except views on rotation

2. Offline-first:
- Built using the repository pattern for data layer ensures that dB is the single source of truth.
- Each news article once fetched is persisted to the dB, all subsequent calls are made to dB instead of network

3. Unit tested: Showcases unit tests for ViewModel and Livedata

4. Modularized: Minimal build times using scalable, modular android architecture


===================
Notes
====================
Answers to the questions asked alongwith the test:

- what were your priorities, and why?

My priorities were to showcase best practices as well as develop the feature requested, and for this reason I decided
to rework the project from ground up. This was because the project was in Java, used MVP and RxJava which I wanted to avoid. 



- if you had another two days, what would you have tackled next?

There are a bunch of shortcuts I've taken with the project, and these are also mentioned as //TODOs where applicable, like:
1. Refactor the Adapter to use Adapter Delegates for different ViewTypes instead of one common adapter.
2. Refactor the data structure for holding news articles grouped by dates instead of a single list.
3. Refactor the Details VM to use single article items instead of reusing the previous list provider. 

What I would have loved to tackle next would be:
1. Add WorkManager and a DailyNewsFetcher Worker, which downloads the news once every 24hours. This is a great usecase since its a news reader application and the user will always have up-to-date news when they open the app.
2. Add simple transition animation to the list-to-detail flow using shared-element-transitions and motion layout.



- what would you change about the structure of the code?
The current code structure follows clean arch principles and is scalable. Although I would like to refactor the repository layer and
have use-cases/interactors instead of directly accessing data-repository.



- what bugs did you find but not fix?
I found some bugs in the old java implementation around RxJava, like passing null values to onNext, etc.
But these were fixed automatically with the refactor.



- what would you change about the visual design of the app?
The design is quite basic as of now. I would like the ability to filter news, have favourites as a seperate tab,
add animations & transitions. I would also love to use gestures like fling to dismiss, swipe to favourite and more, which according
to me would be enjoyable for the end user.


-roughly how long you spent on the exercise?

Roughly, I spent almost 20 to 24 hours overall on the project; 1-2 hours per day for the last 10 days (on & off) since I couldn't find time to work on it at once.
I have also uploaded the project to github as a Private repository for your reference (can be accessed via invite)
It contains around 18 commits, which summarizes and follows all of my design decisions, priorities and coding style.

/end-note
==================


