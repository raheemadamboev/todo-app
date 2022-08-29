<h1 align="center">ToDo</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center">
🎯 "ToDo" app demonstrates modern Android app development with Jetpack Compose, Hilt, Material3, Coroutines, Flows, Room based on MVVM architecture. 
</p>
</br>

<p align="center">
  <img src="/preview/ToDo%201.1.1.jpg"/>
</p>

# Download

You can download the release app on Google Play Store:

<a href="https://play.google.com/store/apps/details?id=xyz.teamgravity.todo">
  <img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" width="200"/>
</a>

# Tech stack

- [Kotlin](https://kotlinlang.org/): first class programming language for native Android development.
- [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines): structured concurrency.
- [Kotlin Flows](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/): reactive communication.
- [Material3](https://m3.material.io/): modern UI/UX guidelines and components.
- [Jetpack Compose](https://developer.android.com/jetpack/compose): modern, declarative way of building UI in Kotlin.
- [Jetpack Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle): observe Android lifecycles and handle UI states upon the lifecycle changes.
- [Jetpack ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
- [Jetpack SavedStateHandle](https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate): in case of process death, key-value map that allows to write and retrieve bundle data to and from the saved state.
- [Jetpack DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore): uses Kotlin Coroutines and Flows to store data asynchronously, consistently, and transactionally.
- [Room](https://developer.android.com/training/data-storage/room): SQLite abstraction and database solution.
- [Dagger Hilt](https://dagger.dev/hilt/): first class dependency injection for native Android development.
- [Firebase](https://firebase.google.com/): tracks analytics and crashes using the Firebase services.
- [Compose Destinations](https://composedestinations.rafaelcosta.xyz/): a type-safe navigation for composables.
- [Timber](https://github.com/JakeWharton/timber): a logger with a small, extensible API.

# Architecture

"ToDo" is based on the MVVM architecture pattern, Repository pattern, Mapper pattern.

<img src="/preview/mvvm-pattern.png"/>

# MAD Score

<p align="center">
  <img src="/preview/summary_1.png"/>
  <img src="/preview/summary_2.png"/>
</p>

# About

A beautifully simple ToDo application that emphasises simplicity and ease of use. Whether you want a shopping list, grocery list or you just have lots of things to remember ToDo is built for you. ToDo is fully offline as it does not require internet connection. Also, ToDo is very fast and very light! The size of APK is lighter than 2 MB. It also supports Dark Mode and multiple languages (English, Russian, Uzbek).

Personally, I use ToDo app to save the urls of articles that I plan to read later. So, I can mark them as completed and everything is tracked also saved in the database. So ToDo can be used for different problems, I hope it can solve your problem or make your life easier!

# Features

- Add, edit and delete tasks.
- Undo deleted tasks.
- Mark tasks as completed. Completed tasks have "Checked/Done" indicator and it is possible to hide them from list.
- Mark tasks as "Important". Important tasks are listed first and they will have red "Warning" indicator.
- Hide completed tasks from the list.
- Sort tasks by name and by created date.
- Your preferences are saved.
- Search for your tasks easily.
- Delete all tasks at once, delete all completed tasks at once, or delete a task by swiping it away.
- Beautiful animations and indicators.
- Dark/Light theme support.
- Dynamic theme support.
- Multiple language support.
- Multiple screen sizes support.
- Light that is less than 2 MB.
- Simplicity that has only three screens and the functions that is needed.
- Adaptive screens that change according to screen orientation.

# License

```xml
Designed and developed by raheemadamboev (Raheem) 2022.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
