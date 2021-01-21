# todo-app
Simple app to track ToDo list

**To-Do**

<a href="https://play.google.com/store/apps/details?id=xyz.teamgravity.todo">Demo app</a>

Many say activity and fragment only should be responsible for updating UI and it should not make decisions and should not do business logic. So in this project all business logic is done by viewmodels. To respond events Kotlin Channel and Kotlin Flow is used. This app is robust and can survive configuration changes and process death. Fragments should be used over activities because they are light and has many features. Jetpack Navigation Component is used for dialog, fragments. As SharedPreferences deprecated, Jetpack DataStore Preferences is used.

<img src="https://i.imgur.com/M3ZuATK.png" alt="Italian Trulli" width="200" height="200">

A beautifully simple todo list app
ToDo is a simple tasks list app that emphasises simplicity and ease of use. Whether you want a shopping list, grocery list or you just have lots of things to remember ToDo is built for you. With ToDo you can build powerful lists, search, sort, re-prioritise or swiping to delete. Enjoy!

**Screenshots:**

<img src="https://i.imgur.com/WzLjzdU.jpg" alt="Italian Trulli" width="200" height="434"> <img src="https://i.imgur.com/JnIMkFd.jpg" alt="Italian Trulli" width="200" height="434"> <img src="https://i.imgur.com/6Rjwq6b.jpg" alt="Italian Trulli" width="200" height="434"> <img src="https://i.imgur.com/e81XD4P.jpg" alt="Italian Trulli" width="200" height="434">

**Key points:**

- MVVM
- Firebase
- Dependency Injection (Hilt)
- Jetpack Navigation Component
- Kotlin Coroutines
- Room
- Jetpack DataStore Preferences
- ViewBinding
- Flow
- StateFlow
- Channel
- Livedate
- Process Death Handling
- Material Design
- Git
