package xyz.teamgravity.todo.data.local

import androidx.room.TypeConverter
import java.util.*

class TodoConverters {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(date: Long): Date {
        return Date(date)
    }
}