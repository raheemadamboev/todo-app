package xyz.teamgravity.todo.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    @FullTimeFormatter
    fun provideFullTimeFormatter(months: DateFormatSymbols): SimpleDateFormat =
        SimpleDateFormat("yyyy, MMMM d, HH:mm", Locale.getDefault()).apply {
            dateFormatSymbols = months
        }
}