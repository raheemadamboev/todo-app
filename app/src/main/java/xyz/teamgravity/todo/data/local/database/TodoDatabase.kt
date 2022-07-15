package xyz.teamgravity.todo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.teamgravity.todo.data.local.constant.TodoConst
import xyz.teamgravity.todo.data.local.converter.TodoConverter
import xyz.teamgravity.todo.data.local.dao.TodoDao
import xyz.teamgravity.todo.data.local.entity.TodoEntity

@TypeConverters(TodoConverter::class)
@Database(
    entities = [TodoEntity::class],
    version = TodoConst.VERSION
)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}