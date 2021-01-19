package xyz.teamgravity.todo.helper.converter

import androidx.room.TypeConverter
import java.util.*

class Converter {

    @TypeConverter
    fun fromDate(date: Date) = date.time

    @TypeConverter
    fun toDate(date: Long) = Date(date)
}