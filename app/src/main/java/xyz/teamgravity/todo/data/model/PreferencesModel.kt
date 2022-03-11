package xyz.teamgravity.todo.data.model

import xyz.teamgravity.todo.viewmodel.room.TaskSort

data class PreferencesModel(
    val sort: TaskSort,
    val hideCompleted: Boolean,
    val language: String
)
