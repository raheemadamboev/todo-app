package xyz.teamgravity.todo.injection.provide

import android.app.Application
import androidx.paging.PagingConfig
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import xyz.teamgravity.coresdkandroid.crypto.CryptoManager
import xyz.teamgravity.coresdkandroid.preferences.Preferences
import xyz.teamgravity.coresdkandroid.review.ReviewManager
import xyz.teamgravity.coresdkandroid.update.UpdateManager
import xyz.teamgravity.todo.core.constant.PagingConst
import xyz.teamgravity.todo.data.local.preferences.AppPreferences
import xyz.teamgravity.todo.data.local.todo.callback.TodoCallback
import xyz.teamgravity.todo.data.local.todo.constant.TodoDatabaseConst
import xyz.teamgravity.todo.data.local.todo.dao.TodoDao
import xyz.teamgravity.todo.data.local.todo.database.TodoDatabase
import xyz.teamgravity.todo.data.repository.TodoRepository
import xyz.teamgravity.todo.injection.name.ApplicationScope
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
        name = TodoDatabaseConst.NAME
    ).addMigrations()
        .addCallback(todoCallback)
        .build()

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @Provides
    @Singleton
    fun provideTodoCallback(
        application: Application,
        todoDatabase: Provider<TodoDatabase>,
        @ApplicationScope applicationScope: CoroutineScope
    ): TodoCallback = TodoCallback(
        application = application,
        db = todoDatabase,
        scope = applicationScope
    )

    @Provides
    @Singleton
    fun provideTodoDao(todoDatabase: TodoDatabase): TodoDao = todoDatabase.todoDao()

    @Provides
    @Singleton
    fun providePagingConfig(): PagingConfig = PagingConfig(
        pageSize = PagingConst.PAGE_SIZE,
        prefetchDistance = PagingConst.PREFETCH_DISTANCE,
        maxSize = PagingConst.MAX_SIZE,
        enablePlaceholders = PagingConst.ENABLE_PLACEHOLDERS
    )

    @Provides
    @Singleton
    fun provideTodoRepository(
        todoDao: TodoDao,
        pagingConfig: PagingConfig
    ): TodoRepository = TodoRepository(
        dao = todoDao,
        config = pagingConfig
    )

    @Provides
    @Singleton
    fun provideTimberDebugTree(): Timber.DebugTree = Timber.DebugTree()

    @Provides
    @Singleton
    fun provideUpdateManager(application: Application): UpdateManager = UpdateManager(application)

    @Provides
    @Singleton
    fun provideCryptoManager(): CryptoManager = CryptoManager()

    @Provides
    @Singleton
    fun providePreferences(
        cryptoManager: CryptoManager,
        application: Application
    ): Preferences = Preferences(
        crypto = cryptoManager,
        context = application
    )

    @Provides
    @Singleton
    fun provideReviewManager(
        preferences: Preferences,
        application: Application
    ): ReviewManager = ReviewManager(
        preferences = preferences,
        context = application
    )

    @Provides
    @Singleton
    fun provideAppPreferences(preferences: Preferences): AppPreferences = AppPreferences(preferences)
}