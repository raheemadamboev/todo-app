package xyz.teamgravity.todo.model

import xyz.teamgravity.todo.viewmodel.TaskSort

data class PreferencesModel(
    val sort: TaskSort,
    val hideCompleted: Boolean
)
