package xyz.teamgravity.todo.data.local.preferences

import xyz.teamgravity.coresdkandroid.preferences.PreferencesKey
import xyz.teamgravity.todo.core.constant.TodoSort

enum class AppPreferencesKey(
    override val key: String,
    override val default: Any?,
    override val encrypted: Boolean
) : PreferencesKey {
    Sorting(
        key = "xyz.teamgravity.todo.Sorting",
        default = TodoSort.Date.name,
        encrypted = false
    ),
    HideCompleted(
        key = "xyz.teamgravity.todo.HideCompleted",
        default = false,
        encrypted = false
    );
}