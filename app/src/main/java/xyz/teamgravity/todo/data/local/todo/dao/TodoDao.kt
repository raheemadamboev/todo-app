package xyz.teamgravity.todo.data.local.todo.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import xyz.teamgravity.todo.core.constant.TodoSort
import xyz.teamgravity.todo.data.local.todo.constant.TodoDatabaseConst.TABLE_TODO
import xyz.teamgravity.todo.data.local.todo.entity.TodoEntity

@Dao
interface TodoDao {

    ///////////////////////////////////////////////////////////////////////////
    // Insert
    ///////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    ///////////////////////////////////////////////////////////////////////////
    // Update
    ///////////////////////////////////////////////////////////////////////////

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodo(todo: TodoEntity)

    ///////////////////////////////////////////////////////////////////////////
    // Delete
    ///////////////////////////////////////////////////////////////////////////

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Query("DELETE FROM $TABLE_TODO WHERE completed = 1")
    suspend fun deleteAllCompletedTodo()

    @Query("DELETE FROM $TABLE_TODO")
    suspend fun deleteAllTodo()

    ///////////////////////////////////////////////////////////////////////////
    // Get
    ///////////////////////////////////////////////////////////////////////////

    fun getTodos(
        query: String,
        hideCompleted: Boolean,
        sorting: TodoSort
    ): PagingSource<Int, TodoEntity> {
        return when (sorting) {
            TodoSort.Name -> getTodosSortedByName(
                query = query,
                hideCompleted = hideCompleted
            )

            TodoSort.Date -> getTodosSortedByDate(
                query = query,
                hideCompleted = hideCompleted
            )
        }
    }

    @Query("SELECT * FROM $TABLE_TODO WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :query || '%' ORDER BY important DESC, name ASC")
    fun getTodosSortedByName(
        query: String,
        hideCompleted: Boolean
    ): PagingSource<Int, TodoEntity>

    @Query("SELECT * FROM $TABLE_TODO WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :query || '%' ORDER BY important DESC, timestamp ASC")
    fun getTodosSortedByDate(
        query: String,
        hideCompleted: Boolean
    ): PagingSource<Int, TodoEntity>
}