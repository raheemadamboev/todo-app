package xyz.teamgravity.todo.data.local.callback

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.data.local.database.TodoDatabase
import xyz.teamgravity.todo.data.local.entity.TodoEntity
import javax.inject.Provider

class TodoCallback(
    private val db: Provider<TodoDatabase>,
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val dao = this.db.get().todoDao()

        scope.launch {
            dao.insertTodo(TodoEntity(name = "Be organized and efficient", important = true))
            dao.insertTodo(TodoEntity(name = "Будьте организованы и эффективны", important = true))
            dao.insertTodo(TodoEntity(name = "Tartibli va samarali bo'ling", important = true))
        }
    }
}