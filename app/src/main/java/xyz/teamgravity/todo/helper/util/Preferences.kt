package xyz.teamgravity.todo.helper.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import xyz.teamgravity.todo.data.model.PreferencesModel
import xyz.teamgravity.todo.viewmodel.room.TaskSort
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(@ApplicationContext context: Context) {
    companion object {
        /**
         *  Preferences name
         */
        private const val PREFS = "prefs"

        /**
         * Task sort order
         */
        private val TASK_SORT = stringPreferencesKey("taskSort")

        /**
         * Task hide completed
         */
        private val HIDE_COMPLETED = booleanPreferencesKey("hideCompleted")

        /**
         * Language
         */
        private val LANGUAGE = stringPreferencesKey("language")
    }

    /**
     * Create data store
     */
    private val dataStore = context.createDataStore(PREFS)

    /**
     * Get data store
     */
    val preferencesFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val taskSort = TaskSort.valueOf(preferences[TASK_SORT] ?: TaskSort.BY_DATE.name)

            val hideCompleted = preferences[HIDE_COMPLETED] ?: false

            val language = preferences[LANGUAGE] ?: "def"

            PreferencesModel(taskSort, hideCompleted, language)
        }


    /**
     * Edit preferences
     */

    // update task sort
    suspend fun updateTaskSort(sort: TaskSort) {
        dataStore.edit { preferences ->
            preferences[TASK_SORT] = sort.name
        }
    }

    // update hide completed
    suspend fun updateHideCompleted(hideCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[HIDE_COMPLETED] = hideCompleted
        }
    }

    // update language
    suspend fun updateLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }
}