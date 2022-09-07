package xyz.teamgravity.todo.data.local.todo.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import xyz.teamgravity.todo.data.local.preferences.TodoSort
import xyz.teamgravity.todo.data.local.todo.constant.TodoConst.TABLE_TODO
import xyz.teamgravity.todo.data.local.todo.entity.TodoEntity

@Dao
interface TodoDao {

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    ///////////////////////////////////////////////////////////////////////////
    // UPDATE
    ///////////////////////////////////////////////////////////////////////////

    @Update
    suspend fun updateTodo(todo: TodoEntity)

    ///////////////////////////////////////////////////////////////////////////
    // DELETE
    ///////////////////////////////////////////////////////////////////////////

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Query("DELETE FROM $TABLE_TODO WHERE completed = 1")
    suspend fun deleteAllCompletedTodo()

    @Query("DELETE FROM $TABLE_TODO")
    suspend fun deleteAllTodo()

    ///////////////////////////////////////////////////////////////////////////
    // GET
    ///////////////////////////////////////////////////////////////////////////

    fun getTodos(query: String, hideCompleted: Boolean, sort: TodoSort): Flow<List<TodoEntity>> =
        when (sort) {
            TodoSort.BY_NAME -> getTodosSortedByName(query, hideCompleted)
            TodoSort.BY_DATE -> getTodosSortedByDate(query, hideCompleted)
        }

    @Query("SELECT * FROM $TABLE_TODO WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :query || '%' ORDER BY important DESC, name ASC")
    fun getTodosSortedByName(query: String, hideCompleted: Boolean): Flow<List<TodoEntity>>

    @Query("SELECT * FROM $TABLE_TODO WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :query || '%' ORDER BY important DESC, timestamp ASC")
    fun getTodosSortedByDate(query: String, hideCompleted: Boolean): Flow<List<TodoEntity>>
}