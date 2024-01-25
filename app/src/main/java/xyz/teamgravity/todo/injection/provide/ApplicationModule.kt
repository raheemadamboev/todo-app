package xyz.teamgravity.todo.injection.provide

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.data.local.preferences.Preferences
import xyz.teamgravity.todo.data.local.todo.callback.TodoCallback
import xyz.teamgravity.todo.data.local.todo.constant.TodoConst
import xyz.teamgravity.todo.data.local.todo.dao.TodoDao
import xyz.teamgravity.todo.data.local.todo.database.TodoDatabase
import xyz.teamgravity.todo.data.repository.TodoRepository
import xyz.teamgravity.todo.injection.name.ApplicationScope
import java.text.DateFormatSymbols
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(
        application: Application,
        todoCallback: TodoCallback
    ): TodoDatabase = Room.databaseBuilder(
        context = application,
        klass = TodoDatabase::class.java,
        name = TodoConst.NAME
    ).addMigrations()
        .addCallback(todoCallback)
        .build()

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Provides
    @Singleton
    fun provideTodoCallback(
        todoDatabase: Provider<TodoDatabase>,
        @ApplicationScope applicationScope: CoroutineScope
    ): TodoCallback = TodoCallback(
        db = todoDatabase,
        scope = applicationScope
    )

    @Provides
    @Singleton
    fun provideTodoDao(todoDatabase: TodoDatabase): TodoDao = todoDatabase.todoDao()

    @Provides
    @Singleton
    fun provideTodoRepository(todoDao: TodoDao): TodoRepository = TodoRepository(todoDao)

    @Provides
    @Singleton
    fun providePreferences(application: Application): Preferences = Preferences(application)

    @Provides
    @Singleton
    fun provideDateFormatSymbols(application: Application): DateFormatSymbols = DateFormatSymbols().apply {
        months = application.resources.getStringArray(R.array.months)
    }

    @Provides
    @Singleton
    fun provideTimberDebugTree(): Timber.DebugTree = Timber.DebugTree()
}