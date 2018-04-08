<img alt="Icon" src="app/src/main/res/mipmap-xxhdpi/ic_launcher.png?raw=true" align="left" hspace="1" vspace="1">

# Hostel

[![License Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=true)](http://www.apache.org/licenses/LICENSE-2.0)
![minSdkVersion 16](https://img.shields.io/badge/minSdkVersion-16-red.svg?style=true)
![compileSdkVersion 27](https://img.shields.io/badge/compileSdkVersion-27-yellow.svg?style=true)
[![CircleCI](https://circleci.com/gh/andremion/Hostel.svg?style=svg)](https://circleci.com/gh/andremion/Hostel)
[![codecov](https://codecov.io/gh/andremion/Hostel/graph/badge.svg)](https://codecov.io/gh/andremion/Hostel)

## Structure

This project has two Android modules:

### [app]
 
- Contains presentation, Android platform and [dependency injection] classes.

The ViewModels are used to consume reactive streams from [repository] and update observable fields for data binding. 

### [data]
 
- Contains entities, repository, data sources (remote and local) classes.

Since the database is empty, the repository will request the data from remote data source and then save into local data source.
The repository exposes only reactive [entities].

The local data source saves data into database.
The [local] data source has its own models used internally as well as the [remote] data source has its own.
The local data source uses database that saves data in [disk] while in testings is used a database that saves in [memory].   

## Libraries and tools used

### Android

* [Android Support Library](https://developer.android.com/topic/libraries/support-library/index.html)
Provides additional convenience classes and features not available in the standard Framework API for easier development and support across more devices.
* [Data Binding](https://developer.android.com/topic/libraries/data-binding)
Write declarative layouts and minimize the glue code necessary to bind application logic and layouts.

### Architecture and Design

* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
A collection of libraries that help you design robust, testable, and maintainable apps.
Start with classes for managing your UI component lifecycle and handling data persistence.
* [Dagger](https://google.github.io/dagger)
A fully static, compile-time dependency injection framework for both Java and Android.

### Reactive

* [RX Java](https://github.com/ReactiveX/RxJava)
A library for composing asynchronous and event-based programs using observable sequences for the Java VM.
* [RX Android](https://github.com/ReactiveX/RxAndroid)
RxJava bindings for Android.

### View and Image

* [ConstraintLayout](https://developer.android.com/training/constraint-layout/index.html)
Allows you to create large and complex layouts with a flat view hierarchy (no nested view groups).
* [RecyclerView](http://developer.android.com/reference/android/support/v7/widget/RecyclerView.html)
A flexible view for providing a limited window into a large data set.
* [Glide](https://github.com/bumptech/glide)
An image loading and caching library for Android focused on smooth scrolling.

### Data Request

* [Retrofit](http://square.github.io/retrofit/)
A type-safe HTTP client for Android and Java.
* [OkHttp](http://square.github.io/okhttp/)
An HTTP & HTTP/2 client for Android and Java applications.
* [Gson](https://github.com/google/gson)
A Java serialization/deserialization library to convert Java Objects into JSON and back.

### Persistence

* [Room](https://developer.android.com/topic/libraries/architecture/room.html)
The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.

### Testing

* [Mockito](https://github.com/mockito/mockito)
Tasty mocking framework for unit tests in Java.

## License

    Copyright 2018 André Mion

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
[app]: app "App module"
[dependency injection]: app/src/main/java/com/andremion/hostel/app/internal/injection "Dependency injection classes"
[data]: data "Data module"
[entities]: data/src/main/java/com/andremion/hostel/data/entity "Entities"
[repository]: data/src/main/java/com/andremion/hostel/data/repository "Repository"
[local]: data/src/main/java/com/andremion/hostel/data/local "Local data source"
[disk]: data/src/main/java/com/andremion/hostel/data/local/database/disk "Disk database"
[memory]: data/src/main/java/com/andremion/hostel/data/local/database/memory "Memory database"
[remote]: data/src/main/java/com/andremion/hostel/data/remote "Remote data source"
