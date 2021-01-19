package xyz.teamgravity.todo.viewmodel

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.injection.ApplicationScope
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
            // insert boys
        }
    }
}