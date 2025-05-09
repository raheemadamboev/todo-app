package xyz.teamgravity.todo.data.local.preferences

import xyz.teamgravity.coresdkandroid.preferences.PreferencesKey
import xyz.teamgravity.todo.core.constant.TodoSort

enum class AppPreferencesKey(
    override val key: String,
    override val default: Any?,
    override val encrypted: Boolean
) : PreferencesKey {
    Sorting(
        key = "sorting",
        default = TodoSort.Date.name,
        encrypted = false
    ),
    HideCompleted(
        key = "hide_completed",
        default = false,
        encrypted = false
    );
}