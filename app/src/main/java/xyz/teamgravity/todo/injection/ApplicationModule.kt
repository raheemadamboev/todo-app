package xyz.teamgravity.todo.injection

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.data.local.TodoCallback
import xyz.teamgravity.todo.data.local.TodoConst
import xyz.teamgravity.todo.data.local.TodoDao
import xyz.teamgravity.todo.data.local.TodoDatabase
import xyz.teamgravity.todo.data.preferences.Preferences
import xyz.teamgravity.todo.data.repository.TodoRepository
import java.text.DateFormatSymbols
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(application: Application, todoCallback: TodoCallback): TodoDatabase =
        Room.databaseBuilder(application, TodoDatabase::class.java, TodoConst.NAME)
            .addMigrations()
            .addCallback(todoCallback)
            .build()

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideTodoCallback(
        todoDatabase: Provider<TodoDatabase>,
        @ApplicationScope applicationScope: CoroutineScope
    ): TodoCallback = TodoCallback(db = todoDatabase, scope = applicationScope)

    @Provides
    @Singleton
    fun provideTodoDao(todoDatabase: TodoDatabase): TodoDao = todoDatabase.todoDao()

    @Provides
    @Singleton
    fun provideTodoRepository(todoDao: TodoDao): TodoRepository = TodoRepository(todoDao)

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext applicationContext: Context): Preferences = Preferences(applicationContext)

    @Provides
    @Singleton
    fun provideMonthFormatter(application: Application): DateFormatSymbols = DateFormatSymbols().apply {
        months = application.resources.getStringArray(R.array.months)
    }
}