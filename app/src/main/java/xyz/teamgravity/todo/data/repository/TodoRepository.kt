package xyz.teamgravity.todo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import xyz.teamgravity.todo.core.constant.TodoSort
import xyz.teamgravity.todo.data.local.todo.dao.TodoDao
import xyz.teamgravity.todo.data.mapper.toEntity
import xyz.teamgravity.todo.data.mapper.toModel
import xyz.teamgravity.todo.data.model.TodoModel

class TodoRepository(
    private val dao: TodoDao,
    private val config: PagingConfig
) {

    ///////////////////////////////////////////////////////////////////////////
    // Insert
    ///////////////////////////////////////////////////////////////////////////

    suspend fun insertTodo(todo: TodoModel) {
        withContext(Dispatchers.IO) {
            dao.insertTodo(todo.toEntity())
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Update
    ///////////////////////////////////////////////////////////////////////////

    suspend fun updateTodo(todo: TodoModel) {
        withContext(Dispatchers.IO) {
            dao.updateTodo(todo.toEntity())
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Delete
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteTodo(todo: TodoModel) {
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
    // Get
    ///////////////////////////////////////////////////////////////////////////

    fun getTodos(
        query: String,
        hideCompleted: Boolean,
        sorting: TodoSort
    ): Flow<PagingData<TodoModel>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                dao.getTodos(
                    query = query,
                    hideCompleted = hideCompleted,
                    sorting = sorting
                )
            }
        ).flow.map { entities ->
            entities.map { entity ->
                entity.toModel()
            }
        }
    }
}