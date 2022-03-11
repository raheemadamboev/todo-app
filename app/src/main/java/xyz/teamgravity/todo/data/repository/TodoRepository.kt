package xyz.teamgravity.todo.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.teamgravity.todo.data.local.TodoDao
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.viewmodel.room.TaskSort

class TodoRepository(
    private val dao: TodoDao
) {

    ///////////////////////////////////////////////////////////////////////////
    // Insert
    ///////////////////////////////////////////////////////////////////////////

    suspend fun insertTodoSync(todo: TodoModel) {
        dao.insertTodo(todo)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Update
    ///////////////////////////////////////////////////////////////////////////

    suspend fun updateTodoSync(todo: TodoModel) {
        dao.updateTodo(todo)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Delete
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteTodoSync(todo: TodoModel) {
        dao.deleteTodo(todo)
    }

    suspend fun deleteAllCompletedTodo() {
        dao.deleteAllCompletedTodo()
    }

    suspend fun deleteAllTodo() {
        dao.deleteAllTodo()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Get
    ///////////////////////////////////////////////////////////////////////////

    fun getTodos(query: String, hideCompleted: Boolean, sort: TaskSort): Flow<List<TodoModel>> {
        return dao.getTodos(query = query, hideCompleted = hideCompleted, sort = sort)
    }
}