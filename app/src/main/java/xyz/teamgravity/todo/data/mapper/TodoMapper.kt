package xyz.teamgravity.todo.data.mapper

import xyz.teamgravity.todo.data.local.todo.entity.TodoEntity
import xyz.teamgravity.todo.data.model.TodoModel

///////////////////////////////////////////////////////////////////////////
// Model
///////////////////////////////////////////////////////////////////////////

fun TodoEntity.toModel(): TodoModel {
    return TodoModel(
        id = id,
        name = name,
        important = important,
        completed = completed,
        timestamp = timestamp
    )
}

///////////////////////////////////////////////////////////////////////////
// Entity
///////////////////////////////////////////////////////////////////////////

fun TodoModel.toEntity(): TodoEntity {
    return TodoEntity(
        id = id,
        name = name,
        important = important,
        completed = completed,
        timestamp = timestamp
    )
}