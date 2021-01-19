package xyz.teamgravity.todo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import xyz.teamgravity.todo.helper.constants.TaskDatabase
import java.util.*

@Entity(tableName = TaskDatabase.TASK_TABLE)
@Parcelize
data class TaskModel(

    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0,

    val name: String,

    val important: Boolean = false,
    val completed: Boolean = false,

    val timestamp: Date = Date()
) : Parcelable