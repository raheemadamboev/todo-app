package xyz.teamgravity.todo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class TodoModel(
    val id: Long = 0,
    val name: String = "",
    val important: Boolean = false,
    val completed: Boolean = false,
    val timestamp: LocalDateTime = LocalDateTime.now()
) : Parcelable