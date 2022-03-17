# To-Do

A beautifully simple ToDo application that emphasises simplicity and ease of use. Whether you want a shopping list, grocery list or you just have lots of things to remember ToDo is built for you. ToDo is fully offline as it does not require internet connection. Also, ToDo is very fast and very light! The size of APK is lighter than 2 MB. It also supports Dark Mode and multiple languages (English, Russian, Uzbek).

Personally, I use ToDo application to save the urls of articles that I plan to read later. So, I can mark them as completed and everything is tracked also saved in the database. So ToDo can be used for different problems, I hope it can solve your problem or make your life easier!

**Features:**

- Add, edit and delete tasks.
- Undo deleted tasks.
- Mark tasks as completed. Completed tasks have "Checked/Done" indicator and it is possible to hide them from list.
- Mark tasks as "Important". Important tasks are listed first and they will have red "Warning" indicator.
- Hide completed tasks from the list.
- Sort task by name and by created date.
- Your preferences are saved.
- Search for your tasks easily.
- Delete all tasks at once, delete all completed tasks at once, or delete a task by swiping it away.
- Beautiful animations and indicators.
- Dark/Light theme support.
- Multiple language support.
- Multiple screen sizes support.
- Light that is less than 2 MB.
- Simplicity that has only three screens and the functions that is needed.

# 1.1 - Jetpack Compose

<a href="https://github.com/raheemadamboev/todo-app/blob/1.1/app/release/app-release.apk">Demo app 1.1 Jetpack Compose</a>

**Screenshots:**

<p align="center">
 <img src="https://github.com/raheemadamboev/todo-app/blob/master/banner/ToDo%201.1.jpg" width="869" height="416" />
</p>

**Tech stack:**

- Jetpack Compose. Declarative way of building UI in only Kotlin which is way better than legacy, old View system.
- Compose Destinations. Very awesome library that helps us with navigation with composables. It is built on the top of Jetpack Compose Navigation that is provide by Google. But this library has more advantages over the plain one such as sending serialazables, parcealables, animations, reduces boilerplate code and others. [Read more about Compose Destinations](https://github.com/raamcosta/compose-destinations)
- MVVM architectural pattern. So it is easy to test, refactor, and scale.
- Jetpack ViewModel. So our state can survive configuration changes and process death as Jetpack ViewModels lifecycle is different from Activity/Fragments lifecycle. Jetpack ViewModel does not get destroyed when configuration change appears. Also, it is very easy to inject dependencies to Jetpack ViewModel using Jetpack Dagger Hilt framework. Also, we can use special Kotlin Coroutine scope called viewModelScope to execute suspendable functions, this scope is destroyed when its parent ViewModel is destroyed.
- SavedStateHandle. So we can easily get our bundle arguments that was passed to Fragment in our Jetpack ViewModel. Also we can easily inject SavedStateHandle to ViewModel using Hilt. We can also save our state and if process death occurs, we can easily restore our state using SavedStateHandle
- Firebase Analytics. I'm analytical person.
- R8 (ProGuard). So we can obfuscate, shrink, and remove unused code from libraries resulting on lighter application. Obfuscation means that others can get original source code from our released APK by reverse engineering it.
- Dagger Hilt. Another Jetpack library that is used for Dependency Injection. It integrates with other Jetpack Libraries very well. And, it removes the complexity of original Dagger library by using annotations heavily. Thus, it can save time in our development.
- Kotlin Coroutines. Kotlin Coroutines are used in order to execute code concurrently such as communicating with Room database and Kotlin Flows, Jetpack DataStore which could block Main/UI Thread.
- Room. Another Jetpack library that provides us easy implementation of SQLite Database. It is built on the top of SQLite Database and makes dealing with database very easy using DAO interfaces.
- Jetpack DataStore Preferences. As SharedPreferences has problems, it is deprecated. Problem was SharedPreferences read values schronously which is crazy! (It could block Main/UI thread). Jetpack DataStore uses Kotlin Flows, so that we can continously observe for changes and integrate (combine) with other flows. Everything is done asynchronously in Jetpack DataStore.
- Kotlin Flows. One of the greatest technology I have ever seen. Very powerful and very easy. Flow is used to observe changes in Jetpack DataStore Preferences and Room Database. StateFlow is used in order to hold states in flow and observe for changes. Channel is used to send only one time events, and Channel is cold flow so our event won't get lost if there is no consumer :)
- Material Design (V2). You know why we need it, UI/UX.
- Git. Because of legend Linus Torvalds invention, we are here. Version Control System is used to track changes in files.

# 1.0 - View (XML)

<a href="https://github.com/raheemadamboev/todo-app/blob/1.0/app/release/app-release.apk">Demo app 1.0 View (XML)</a>

**Screenshots:**

<p align="center">
<img src="https://github.com/raheemadamboev/todo-app/blob/master/banner/ToDo%201.0.jpg" width="869" height="416">
</p>

**Tech stack:**

- ViewBinding. Type-safe, null-safe, efficient (it reduces boilerplate findViewById<>() code and saves time) way to get Views in Kotlin code. Also calling findViewById<>() everytime when we want to get view in Kotlin code is very expensive. ViewBinding solves that problem. Thank you ViewBinding!
- LiveData. I don't remember why the hell I used LiveData when there is StateFlow!
- Fragments. Fragments are used over Activities as they are lighter and can be used effectively. We can also nest them which is crazy!
- Jetpack Navigation Component. Dealing with Fragments without Jetpack Navigation Component is pain! Navigating to fragments, passing arguments, animations, backstack and everything related to fragments are handled by Jetpack Navigation Component.
- MVVM architectural pattern. So it is easy to test, refactor, and scale.
- Jetpack ViewModel. So our state can survive configuration changes and process death as Jetpack ViewModels lifecycle is different from Activity/Fragments lifecycle. Jetpack ViewModel does not get destroyed when configuration change appears. Also, it is very easy to inject dependencies to Jetpack ViewModel using Jetpack Dagger Hilt framework. Also, we can use special Kotlin Coroutine scope called viewModelScope to execute suspendable functions, this scope is destroyed when its parent ViewModel is destroyed.
- SavedStateHandle. So we can easily get our bundle arguments that was passed to Fragment in our Jetpack ViewModel. Also we can easily inject SavedStateHandle to ViewModel using Hilt. We can also save our state and if process death occurs, we can easily restore our state using SavedStateHandle
- Firebase Analytics. I'm analytical person.
- R8 (ProGuard). So we can obfuscate, shrink, and remove unused code from libraries resulting on lighter application. Obfuscation means that others can get original source code from our released APK by reverse engineering it.
- Dagger Hilt. Another Jetpack library that is used for Dependency Injection. It integrates with other Jetpack Libraries very well. And, it removes the complexity of original Dagger library by using annotations heavily. Thus, it can save time in our development.
- Kotlin Coroutines. Kotlin Coroutines are used in order to execute code concurrently such as communicating with Room database and Kotlin Flows, Jetpack DataStore which could block Main/UI Thread.
- Room. Another Jetpack library that provides us easy implementation of SQLite Database. It is built on the top of SQLite Database and makes dealing with database very easy using DAO interfaces.
- Jetpack DataStore Preferences. As SharedPreferences has problems, it is deprecated. Problem was SharedPreferences read values schronously which is crazy! (It could block Main/UI thread). Jetpack DataStore uses Kotlin Flows, so that we can continously observe for changes and integrate (combine) with other flows. Everything is done asynchronously in Jetpack DataStore.
- Kotlin Flows. One of the greatest technology I have ever seen. Very powerful and very easy. Flow is used to observe changes in Jetpack DataStore Preferences and Room Database. StateFlow is used in order to hold states in flow and observe for changes. Channel is used to send only one time events, and Channel is cold flow so our event won't get lost if there is no consumer :)
- Material Design (V2). You know why we need it, UI/UX.
- Git. Because of legend Linus Torvalds invention, we are here. Version Control System is used to track changes in files.
