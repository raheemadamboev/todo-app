package xyz.teamgravity.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.teamgravity.todo.data.model.TodoModel

@TypeConverters(TodoConverters::class)
@Database(
    entities = [TodoModel::class],
    version = TodoConst.VERSION
)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}