package xyz.teamgravity.todo.data.local.todo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.teamgravity.todo.data.local.todo.constant.TodoConst
import java.util.Date

@Entity(TodoConst.TABLE_TODO)
data class TodoEntity(

    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0,
    val name: String,
    val important: Boolean,
    val completed: Boolean,
    val timestamp: Date
)