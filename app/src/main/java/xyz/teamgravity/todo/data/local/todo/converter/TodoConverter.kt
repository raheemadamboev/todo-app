package xyz.teamgravity.todo.data.local.todo.converter

import androidx.room.TypeConverter
import java.util.*

class TodoConverter {

    @TypeConverter
    fun dateToLong(value: Date): Long {
        return value.time
    }

    @TypeConverter
    fun longToDate(value: Long): Date {
        return Date(value)
    }
}