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
import xyz.teamgravity.todo.data.local.TodoDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideMyDatabase(app: Application, callback: TodoCallback) =
        Room.databaseBuilder(app, TodoDatabase::class.java, TodoConst.NAME)
            .addMigrations()
            .addCallback(callback)
            .build()

    @Singleton
    @Provides
    fun provideTaskDao(db: TodoDatabase) = db.todoDao()

    @Singleton
    @Provides
    @ApplicationScope
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}