package xyz.teamgravity.todo.viewmodel.room

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.injection.ApplicationScope
import xyz.teamgravity.todo.model.TaskModel
import javax.inject.Inject
import javax.inject.Provider

class TaskCallback @Inject constructor(
    private val database: Provider<MyDatabase>,
    @ApplicationScope private val applicationScope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val dao = database.get().taskDao()

        applicationScope.launch {
            dao.insert(TaskModel(name = "Be organized and efficient", important = true))
            dao.insert(TaskModel(name = "Будьте организованы и эффективны", important = true))
            dao.insert(TaskModel(name = "Tartibli va samarali bo'ling", important = true))
        }
    }
}