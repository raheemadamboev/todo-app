package xyz.teamgravity.todo.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import xyz.teamgravity.todo.data.local.TodoConst
import xyz.teamgravity.todo.helper.constants.TaskDatabase
import java.util.*

@Parcelize
@Entity(tableName = TodoConst.TABLE_TODO)
data class TodoModel(

    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0,

    val name: String = "",

    val important: Boolean = false,
    val completed: Boolean = false,

    val timestamp: Date = Date()
) : Parcelable