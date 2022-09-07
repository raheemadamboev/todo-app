package xyz.teamgravity.todo.data.local.todo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.teamgravity.todo.data.local.todo.constant.TodoConst
import xyz.teamgravity.todo.data.local.todo.converter.TodoConverter
import xyz.teamgravity.todo.data.local.todo.dao.TodoDao
import xyz.teamgravity.todo.data.local.todo.entity.TodoEntity

@TypeConverters(TodoConverter::class)
@Database(
    entities = [TodoEntity::class],
    version = TodoConst.VERSION
)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}