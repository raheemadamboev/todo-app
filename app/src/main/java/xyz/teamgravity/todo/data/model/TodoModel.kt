package xyz.teamgravity.todo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class TodoModel(
    val id: Long = 0,
    val name: String = "",
    val important: Boolean = false,
    val completed: Boolean = false,
    val timestamp: Date = Date()
) : Parcelable