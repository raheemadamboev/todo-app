package xyz.teamgravity.todo.injection.provide

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import xyz.teamgravity.todo.injection.name.FullTimeFormatter
import java.time.format.DateTimeFormatter
import java.util.Locale

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    @FullTimeFormatter
    fun provideFullTimeFormatter(): DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy, MMMM d, HH:mm", Locale.getDefault())
}