package xyz.teamgravity.todo.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import xyz.teamgravity.todo.data.local.preferences.TodoSort
import xyz.teamgravity.todo.data.local.todo.dao.TodoDao
import xyz.teamgravity.todo.data.mapper.toEntity
import xyz.teamgravity.todo.data.mapper.toModel
import xyz.teamgravity.todo.data.model.TodoModel

class TodoRepository(
    private val dao: TodoDao
) {

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    suspend fun insertTodoSync(todo: TodoModel) {
        withContext(Dispatchers.IO) {
            dao.insertTodo(todo.toEntity())
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // UPDATE
    ///////////////////////////////////////////////////////////////////////////

    suspend fun updateTodoSync(todo: TodoModel) {
        withContext(Dispatchers.IO) {
            dao.updateTodo(todo.toEntity())
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // DELETE
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteTodoSync(todo: TodoModel) {
        withContext(Dispatchers.IO) {
            dao.deleteTodo(todo.toEntity())
        }
    }

    suspend fun deleteAllCompletedTodo() {
        withContext(Dispatchers.IO) {
            dao.deleteAllCompletedTodo()
        }
    }

    suspend fun deleteAllTodo() {
        withContext(Dispatchers.IO) {
            dao.deleteAllTodo()
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GET
    ///////////////////////////////////////////////////////////////////////////

    fun getTodos(query: String, hideCompleted: Boolean, sort: TodoSort): Flow<List<TodoModel>> {
        return dao.getTodos(query = query, hideCompleted = hideCompleted, sort = sort).map { todos -> todos.map { it.toModel() } }
    }
}