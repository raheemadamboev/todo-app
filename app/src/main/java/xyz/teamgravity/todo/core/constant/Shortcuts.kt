package xyz.teamgravity.todo.core.constant

enum class Shortcuts(
    val id: String
) {
    AddTodo("add_todo");

    companion object {
        fun fromId(id: String?): Shortcuts? {
            if (id == null) return null
            return entries.firstOrNull { it.id == id }
        }
    }
}