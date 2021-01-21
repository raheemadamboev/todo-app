package xyz.teamgravity.todo.viewmodel.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.helper.util.Preferences
import xyz.teamgravity.todo.model.TaskModel
import xyz.teamgravity.todo.viewmodel.room.TaskDao
import xyz.teamgravity.todo.viewmodel.room.TaskSort

class TaskListViewModel @ViewModelInject constructor(
    private val dao: TaskDao,
    private val preferences: Preferences,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val SEARCH_QUERY = "searchQuery"
    }

    // query
    val query = state.getLiveData(SEARCH_QUERY, "")

    // preferences flow
    val preferencesFlow = preferences.preferencesFlow

    // channel to send one time events
    private val taskEventChannel = Channel<TaskEvent> { }
    val taskEvent = taskEventChannel.receiveAsFlow()

    // search tasks, combine everything
    private val taskFlow = combine(query.asFlow(), preferencesFlow) { query, preferencesModel ->
        Pair(query, preferencesModel)
    }.flatMapLatest { (query, preferencesModel) ->
        dao.getTasks(query, preferencesModel.hideCompleted, preferencesModel.sort)
    }

    // result as a live data
    val tasks = taskFlow.asLiveData()

    /**
     * Events
     */

    /**
     * Recycler view task card is swiped out
     */
    fun onTaskSwiped(task: TaskModel) = viewModelScope.launch {
        dao.delete(task)
        taskEventChannel.send(TaskEvent.ShowUndoDeleteTaskMessage(task))
    }

    /**
     * Menu sort event
     */
    fun onMenuTaskSort(sort: TaskSort) = viewModelScope.launch {
        preferences.updateTaskSort(sort)
    }

    /**
     * Menu on hide completed touched
     */
    fun onMenuHideCompleted(hideCompleted: Boolean) = viewModelScope.launch {
        preferences.updateHideCompleted(hideCompleted)
    }

    /**
     * Menu delete all completed
     */
    fun onMenuDeleteAllCompleted() = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.NavigateDeleteAllCompleted)
    }

    /**
     * Menu delete all task
     */
    fun onMenuDeleteAllTasks() = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.NavigateDeleteAllTasks)
    }

    /**
     * Menu about me
     */
    fun onMenuAboutMe() = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.NavigateAboutMeScreen)
    }

    /**
     * Menu language click
     */
    fun onMenuLanguage(language: String) = viewModelScope.launch {
        preferences.updateLanguage(language)
        taskEventChannel.send(TaskEvent.ChangeLanguage(language))
    }

    /**
     * Task card click
     */
    fun onTaskCardClick(task: TaskModel) = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.NavigateToAddEditTaskScreen(task))
    }

    /**
     * Task card checked
     */
    fun onTaskCardChecked(task: TaskModel, isChecked: Boolean) = viewModelScope.launch {
        dao.update(task.copy(completed = isChecked))
    }

    /**
     * Add floating action button click
     */
    fun onAddButtonClick() = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.NavigateToAddTaskScreen)
    }

    /**
     * Add or edit result back from fragment
     */
    fun onAddEditTaskResult(message: String) = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.ShowAddEditResultMessage(message))
    }

    /**
     * Snackbar undo action clicked
     */
    fun onUndoDeleteTaskClick(task: TaskModel) = viewModelScope.launch {
        dao.insert(task)
    }

    sealed class TaskEvent {
        object NavigateDeleteAllCompleted : TaskEvent()
        object NavigateDeleteAllTasks : TaskEvent()
        data class ShowUndoDeleteTaskMessage(val task: TaskModel) : TaskEvent()
        object NavigateToAddTaskScreen : TaskEvent()
        data class NavigateToAddEditTaskScreen(val task: TaskModel) : TaskEvent()
        data class ShowAddEditResultMessage(val message: String) : TaskEvent()
        object NavigateAboutMeScreen : TaskEvent()
        data class ChangeLanguage(val language: String) : TaskEvent()
    }
}