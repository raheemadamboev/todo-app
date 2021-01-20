package xyz.teamgravity.todo.helper.util

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    /**
     * Add two string with :
     */
    fun addWithPoint(one: String, two: String) = "$one: $two"

    /**
     * Format time
     */
    fun formatTime(date: Date, months: Array<String>): String {
        val monthFormat = DateFormatSymbols()
        monthFormat.months = months
        val formatter = SimpleDateFormat("yyyy, MMMM d, HH:mm", Locale.getDefault())
        formatter.dateFormatSymbols = monthFormat
        return formatter.format(date)
    }
}