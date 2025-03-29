package xyz.teamgravity.todo.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

class Preferences(context: Context) {

    companion object {
        /**
         *  Preferences name.
         */
        const val PREFS = "prefs"

        /**
         * Determines how to order ToDos.
         */
        private val TODO_SORT: Preferences.Key<String> = stringPreferencesKey("todoSort")
        private val DEFAULT_TODO_SORT: String = TodoSort.Date.name

        /**
         * Determines if completed ToDos are hidden on the List.
         */
        private val HIDE_COMPLETED: Preferences.Key<Boolean> = booleanPreferencesKey("hideCompleted")
        private const val DEFAULT_HIDE_COMPLETED = false
    }

    private val Context.store: DataStore<Preferences> by preferencesDataStore(name = PREFS)
    private val store: DataStore<Preferences> = context.store

    private fun handleIOException(t: Throwable): Preferences {
        return if (t is IOException) emptyPreferences() else throw t
    }

    ///////////////////////////////////////////////////////////////////////////
    // Update
    ///////////////////////////////////////////////////////////////////////////

    suspend fun updateTodoSort(value: TodoSort) {
        withContext(Dispatchers.IO) {
            store.edit { it[TODO_SORT] = value.name }
        }
    }

    suspend fun updateHideCompleted(value: Boolean) {
        withContext(Dispatchers.IO) {
            store.edit { it[HIDE_COMPLETED] = value }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Get
    ///////////////////////////////////////////////////////////////////////////

    val preferences: Flow<PreferencesModel> = store.data
        .catch { emit(handleIOException(it)) }
        .map { preferences ->
            val sort = TodoSort.fromName(preferences[TODO_SORT] ?: DEFAULT_TODO_SORT)
            val hideCompleted = preferences[HIDE_COMPLETED] ?: DEFAULT_HIDE_COMPLETED
            return@map PreferencesModel(
                sort = sort,
                hideCompleted = hideCompleted
            )
        }

    ///////////////////////////////////////////////////////////////////////////
    // Misc
    ///////////////////////////////////////////////////////////////////////////

    data class PreferencesModel(
        val sort: TodoSort,
        val hideCompleted: Boolean
    )
}