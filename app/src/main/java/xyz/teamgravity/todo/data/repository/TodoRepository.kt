package xyz.teamgravity.todo.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.teamgravity.todo.data.local.dao.TodoDao
import xyz.teamgravity.todo.data.local.preferences.TodoSort
import xyz.teamgravity.todo.data.mapper.toEntity
import xyz.teamgravity.todo.data.mapper.toModel
import xyz.teamgravity.todo.data.model.TodoModel

class TodoRepository(
    private val dao: TodoDao
) {

    ///////////////////////////////////////////////////////////////////////////
    // Insert
    ///////////////////////////////////////////////////////////////////////////

    suspend fun insertTodoSync(todo: TodoModel) {
        dao.insertTodo(todo.toEntity())
    }

    ///////////////////////////////////////////////////////////////////////////
    // Update
    ///////////////////////////////////////////////////////////////////////////

    suspend fun updateTodoSync(todo: TodoModel) {
        dao.updateTodo(todo.toEntity())
    }

    ///////////////////////////////////////////////////////////////////////////
    // Delete
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteTodoSync(todo: TodoModel) {
        dao.deleteTodo(todo.toEntity())
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

    fun getTodos(query: String, hideCompleted: Boolean, sort: TodoSort): Flow<List<TodoModel>> {
        return dao.getTodos(query = query, hideCompleted = hideCompleted, sort = sort).map { todos -> todos.map { it.toModel() } }
    }
}