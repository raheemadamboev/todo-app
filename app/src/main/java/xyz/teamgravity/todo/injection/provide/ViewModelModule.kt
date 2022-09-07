package xyz.teamgravity.todo.injection.provide

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import xyz.teamgravity.todo.injection.name.FullTimeFormatter
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    @FullTimeFormatter
    fun provideFullTimeFormatter(dateFormatSymbols: DateFormatSymbols): SimpleDateFormat =
        SimpleDateFormat("yyyy, MMMM d, HH:mm", Locale.getDefault()).apply {
            this.dateFormatSymbols = dateFormatSymbols
        }
}