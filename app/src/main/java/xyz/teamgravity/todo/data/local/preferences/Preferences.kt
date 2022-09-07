package xyz.teamgravity.todo.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = xyz.teamgravity.todo.data.local.preferences.Preferences.PREFS)

class Preferences(context: Context) {

    companion object {
        /**
         *  Preferences name
         */
        const val PREFS = "prefs"

        /**
         * Task sort order
         */
        private val TODO_SORT = stringPreferencesKey("taskSort")
        private val DEFAULT_TODO_SORT = TodoSort.BY_DATE.name

        /**
         * Task hide completed
         */
        private val HIDE_COMPLETED = booleanPreferencesKey("hideCompleted")
        private const val DEFAULT_HIDE_COMPLETED = false
    }

    private val store = context.dataStore

    ///////////////////////////////////////////////////////////////////////////
    // UPDATE
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
    // GET
    ///////////////////////////////////////////////////////////////////////////

    val preferences: Flow<PreferencesModel> = store.data
        .catch { handleIOException(it) }
        .map { preferences ->
            val sort = TodoSort.valueOf(preferences[TODO_SORT] ?: DEFAULT_TODO_SORT)

            val hideCompleted = preferences[HIDE_COMPLETED] ?: DEFAULT_HIDE_COMPLETED

            return@map PreferencesModel(sort = sort, hideCompleted = hideCompleted)
        }

    ///////////////////////////////////////////////////////////////////////////
    // MISC
    ///////////////////////////////////////////////////////////////////////////

    private fun handleIOException(t: Throwable): Preferences {
        return if (t is IOException) emptyPreferences() else throw t
    }

    ///////////////////////////////////////////////////////////////////////////
    // MODEL
    ///////////////////////////////////////////////////////////////////////////

    data class PreferencesModel(
        val sort: TodoSort,
        val hideCompleted: Boolean
    )
}