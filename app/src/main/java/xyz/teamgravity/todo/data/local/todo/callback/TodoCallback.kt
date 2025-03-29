package xyz.teamgravity.todo.data.local.todo.callback

import android.app.Application
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.data.local.todo.database.TodoDatabase
import xyz.teamgravity.todo.data.local.todo.entity.TodoEntity
import java.time.LocalDateTime
import javax.inject.Provider

class TodoCallback(
    private val application: Application,
    private val db: Provider<TodoDatabase>,
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope.launch {
            this@TodoCallback.db.get().todoDao().insertTodo(
                TodoEntity(
                    name = application.getString(R.string.predefined_todo_name),
                    important = true,
                    completed = false,
                    timestamp = LocalDateTime.now()
                )
            )
        }
    }
}