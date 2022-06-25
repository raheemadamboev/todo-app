package xyz.teamgravity.todo.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.data.preferences.TodoSort

@Dao
interface TodoDao {

    ///////////////////////////////////////////////////////////////////////////
    // Insert
    ///////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoModel)

    ///////////////////////////////////////////////////////////////////////////
    // Update
    ///////////////////////////////////////////////////////////////////////////

    @Update
    suspend fun updateTodo(todo: TodoModel)

    ///////////////////////////////////////////////////////////////////////////
    // Delete
    ///////////////////////////////////////////////////////////////////////////

    @Delete
    suspend fun deleteTodo(todo: TodoModel)

    @Query("DELETE FROM ${TodoConst.TABLE_TODO} WHERE completed = 1")
    suspend fun deleteAllCompletedTodo()

    @Query("DELETE FROM ${TodoConst.TABLE_TODO}")
    suspend fun deleteAllTodo()

    ///////////////////////////////////////////////////////////////////////////
    // Get
    ///////////////////////////////////////////////////////////////////////////

    fun getTodos(query: String, hideCompleted: Boolean, sort: TodoSort): Flow<List<TodoModel>> =
        when (sort) {
            TodoSort.BY_NAME -> getTodosSortedByName(query, hideCompleted)
            TodoSort.BY_DATE -> getTodosSortedByDate(query, hideCompleted)
        }

    @Query("SELECT * FROM ${TodoConst.TABLE_TODO} WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :query || '%' ORDER BY important DESC, name ASC")
    fun getTodosSortedByName(query: String, hideCompleted: Boolean): Flow<List<TodoModel>>

    @Query("SELECT * FROM ${TodoConst.TABLE_TODO} WHERE (completed != :hideCompleted or completed = 0) AND name LIKE '%' || :query || '%' ORDER BY important DESC, timestamp ASC")
    fun getTodosSortedByDate(query: String, hideCompleted: Boolean): Flow<List<TodoModel>>
}