package xyz.teamgravity.todo.viewmodel.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.teamgravity.todo.helper.constants.TaskDatabase
import xyz.teamgravity.todo.helper.converter.Converter
import xyz.teamgravity.todo.model.TaskModel

@TypeConverters(Converter::class)
@Database(entities = [TaskModel::class], version = TaskDatabase.DATABASE_VERSION)
abstract class MyDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}