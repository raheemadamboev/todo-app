package xyz.teamgravity.todo.injection

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import xyz.teamgravity.todo.helper.constants.TaskDatabase
import xyz.teamgravity.todo.viewmodel.room.MyDatabase
import xyz.teamgravity.todo.viewmodel.room.TaskCallback
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideMyDatabase(app: Application, callback: TaskCallback) =
        Room.databaseBuilder(app, MyDatabase::class.java, TaskDatabase.DATABASE_NAME)
            .addMigrations()
            .addCallback(callback)
            .build()

    @Singleton
    @Provides
    fun provideTaskDao(db: MyDatabase) = db.taskDao()

    @Singleton
    @Provides
    @ApplicationScope
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}