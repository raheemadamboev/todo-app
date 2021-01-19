package xyz.teamgravity.todo.viewmodel.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import xyz.teamgravity.todo.helper.constants.TaskDatabase
import xyz.teamgravity.todo.model.TaskModel

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskModel)

    @Update
    suspend fun update(task: TaskModel)

    @Delete
    suspend fun delete(task: TaskModel)

    @Query("SELECT * FROM ${TaskDatabase.TASK_TABLE} WHERE (completed != :hideCompleted or completed = 0) AND name LIKE '%' || :query || '%' ORDER BY important DESC, name ASC")
    fun getTasksSortedByName(query: String, hideCompleted: Boolean): Flow<List<TaskModel>>

    @Query("SELECT * FROM ${TaskDatabase.TASK_TABLE} WHERE (completed != :hideCompleted or completed = 0) AND name LIKE '%' || :query || '%' ORDER BY important DESC, timestamp ASC")
    fun getTasksSortedByDate(query: String, hideCompleted: Boolean): Flow<List<TaskModel>>

    fun getTasks(query: String, hideCompleted: Boolean, sort: TaskSort): Flow<List<TaskModel>> =
        when (sort) {
            TaskSort.BY_NAME -> getTasksSortedByName(query, hideCompleted)
            TaskSort.BY_DATE -> getTasksSortedByDate(query, hideCompleted)
        }

    @Query("DELETE FROM ${TaskDatabase.TASK_TABLE} WHERE completed = 1")
    suspend fun deleteAllCompleted()
}