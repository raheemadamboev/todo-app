package xyz.teamgravity.todo.data.local.converter

import androidx.room.TypeConverter
import java.util.*

class TodoConverter {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(date: Long): Date {
        return Date(date)
    }
}