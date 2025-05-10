package xyz.teamgravity.todo.data.local.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.teamgravity.coresdkandroid.preferences.Preferences
import xyz.teamgravity.todo.core.constant.TodoSort

class AppPreferences(
    private val preferences: Preferences
) {

    ///////////////////////////////////////////////////////////////////////////
    // Upsert
    ///////////////////////////////////////////////////////////////////////////

    suspend fun upsertSorting(value: TodoSort) {
        preferences.upsertString(
            key = AppPreferencesKey.Sorting,
            value = value.name
        )
    }

    suspend fun upsertHideCompleted(value: Boolean) {
        preferences.upsertBoolean(
            key = AppPreferencesKey.HideCompleted,
            value = value
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // Get
    ///////////////////////////////////////////////////////////////////////////

    fun getSorting(): Flow<TodoSort> {
        return preferences.getString(AppPreferencesKey.Sorting).map { TodoSort.fromName(it) }
    }

    fun getHideCompleted(): Flow<Boolean> {
        return preferences.getBoolean(AppPreferencesKey.HideCompleted).map { it ?: AppPreferencesKey.HideCompleted.default as Boolean }
    }
}