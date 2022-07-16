package xyz.teamgravity.todo.data.local.converter

import androidx.room.TypeConverter
import java.util.*

class TodoConverter {

    @TypeConverter
    fun fromDateToLong(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun fromLongToDate(date: Long): Date {
        return Date(date)
    }
}