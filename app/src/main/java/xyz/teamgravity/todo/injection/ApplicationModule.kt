package xyz.teamgravity.todo.injection

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import xyz.teamgravity.todo.data.local.TodoCallback
import xyz.teamgravity.todo.data.local.TodoConst
import xyz.teamgravity.todo.data.local.TodoDao
import xyz.teamgravity.todo.data.local.TodoDatabase
import xyz.teamgravity.todo.data.repository.TodoRepository
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application, callback: TodoCallback): TodoDatabase =
        Room.databaseBuilder(app, TodoDatabase::class.java, TodoConst.NAME)
            .addMigrations()
            .addCallback(callback)
            .build()

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideTodoCallback(
        db: Provider<TodoDatabase>,
        @ApplicationScope applicationScope: CoroutineScope
    ): TodoCallback = TodoCallback(db = db, scope = applicationScope)

    @Provides
    @Singleton
    fun provideTodoDao(db: TodoDatabase): TodoDao = db.todoDao()

    @Provides
    @Singleton
    fun provideTodoRepository(dao: TodoDao): TodoRepository = TodoRepository(dao)
}