package xyz.teamgravity.todo.data.local.preferences

enum class TodoSort {
    Name,
    Date;

    companion object {
        fun fromName(name: String): TodoSort {
            return entries.firstOrNull { it.name == name } ?: Name
        }
    }
}