package xyz.teamgravity.todo.viewmodel.viewmodel

import android.content.res.Resources
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.model.TaskModel
import xyz.teamgravity.todo.viewmodel.room.TaskDao

class AddEditTaskViewModel @ViewModelInject constructor(
    @Assisted private val state: SavedStateHandle,
    private val dao: TaskDao
) : ViewModel() {
    companion object {
        private const val TASK = "task"
        private const val TASK_NAME = "taskName"
        private const val TASK_IMPORTANCE = "taskImportance"
    }

    val task = state.get<TaskModel>(TASK)

    var taskName = state.get<String>(TASK_NAME) ?: task?.name ?: ""
        set(value) {
            field = value
            state.set(TASK_NAME, value)
        }

    var taskImportance = state.get<Boolean>(TASK_IMPORTANCE) ?: task?.important ?: false
        set(value) {
            field = value
            state.set(TASK_IMPORTANCE, value)
        }

    // channel to send one time events
    private val addEditTaskEventChannel = Channel<AddEditTaskEvent> { }
    val addEditTaskEvent = addEditTaskEventChannel.receiveAsFlow()

    /**
     * Events
     */

    /**
     * Done floating action button click
     */
    fun onSaveButtonClick(res: Resources) {
        if (taskName.isBlank()) {
            showInvalidInputMessage(res.getString(R.string.error_name))
            return
        }

        // update or create
        if (task != null) {
            val updateTask = task.copy(name = taskName, important = taskImportance)
            updateTask(updateTask, res.getString(R.string.task_updated))
        } else {
            val newTask = TaskModel(name = taskName, important = taskImportance)
            insertTask(newTask, res.getString(R.string.task_created))
        }
    }

    /**
     * Update task
     */
    private fun updateTask(task: TaskModel, message: String) = viewModelScope.launch {
        dao.update(task)
        addEditTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(message))
    }

    /**
     * Insert task
     */
    private fun insertTask(task: TaskModel, message: String) = viewModelScope.launch {
        dao.insert(task)
        addEditTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(message))
    }

    /**
     * Name is blank event
     */
    private fun showInvalidInputMessage(message: String) = viewModelScope.launch {
        addEditTaskEventChannel.send(AddEditTaskEvent.ShowInvalidInputEvent(message))
    }

    sealed class AddEditTaskEvent {
        data class ShowInvalidInputEvent(val message: String) : AddEditTaskEvent()
        data class NavigateBackWithResult(val message: String) : AddEditTaskEvent()
    }
}