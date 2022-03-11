package xyz.teamgravity.todo.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import java.text.SimpleDateFormat
import java.util.*

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    @ActivityScoped
    @FullTimeFormatter
    fun provideFullTimeFormatter(): SimpleDateFormat = SimpleDateFormat("yyyy, MMMM d, HH:mm", Locale.getDefault())
}