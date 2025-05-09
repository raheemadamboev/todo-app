package xyz.teamgravity.todo.presentation.screen.todo.list

import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.teamgravity.coresdkandroid.review.ReviewManager
import xyz.teamgravity.coresdkandroid.update.UpdateManager
import xyz.teamgravity.todo.core.constant.TodoSort
import xyz.teamgravity.todo.data.local.preferences.AppPreferences
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.data.repository.TodoRepository
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val preferences: AppPreferences,
    private val review: ReviewManager,
    private val update: UpdateManager
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    var todos: ImmutableList<TodoModel> by mutableStateOf(persistentListOf())
        private set

    var searchExpanded: Boolean by mutableStateOf(false)
        private set

    var menuExpanded: Boolean by mutableStateOf(false)
        private set

    var sortExpanded: Boolean by mutableStateOf(false)
        private set

    var hideCompleted: Boolean by mutableStateOf(false)
        private set

    var reviewShown: Boolean by mutableStateOf(false)
        private set

    var updateAvailableType: UpdateManager.Type by mutableStateOf(UpdateManager.Type.None)
        private set

    var updateDownloadedShown: Boolean by mutableStateOf(false)
        private set

    var deleteCompletedShown: Boolean by mutableStateOf(false)
        private set

    var deleteAllShown: Boolean by mutableStateOf(false)
        private set

    private val _event = Channel<TodoListEvent>()
    val event: Flow<TodoListEvent> = _event.receiveAsFlow()

    private var deletedTodo: TodoModel? = null

    init {
        observe()
        monitor()
    }

    private fun observe() {
        observeReviewEvent()
        observeUpdateEvent()
        observeQueryAndPreferences()
    }

    private fun monitor() {
        review.monitor()
    }

    private fun handleReviewEvent(event: ReviewManager.ReviewEvent) {
        when (event) {
            ReviewManager.ReviewEvent.Eligible -> {
                reviewShown = true
            }
        }
    }

    private suspend fun handleUpdateEvent(event: UpdateManager.UpdateEvent) {
        when (event) {
            is UpdateManager.UpdateEvent.Available -> {
                updateAvailableType = event.type
            }

            UpdateManager.UpdateEvent.StartDownload -> {
                _event.send(TodoListEvent.DownloadAppUpdate)
            }

            UpdateManager.UpdateEvent.Downloaded -> {
                updateDownloadedShown = true
            }
        }
    }

    private fun observeReviewEvent() {
        viewModelScope.launch {
            review.event.collect { event ->
                handleReviewEvent(event)
            }
        }
    }

    private fun observeUpdateEvent() {
        viewModelScope.launch {
            update.event.collect { event ->
                handleUpdateEvent(event)
            }
        }
    }

    private fun observeQueryAndPreferences() {
        viewModelScope.launch {
            combine(query, preferences.getSorting(), preferences.getHideCompleted()) { query, sorting, hideCompleted ->
                Triple(query, sorting, hideCompleted)
            }.flatMapLatest { (query, sorting, hideCompleted) ->
                this@TodoListViewModel.hideCompleted = hideCompleted
                repository.getTodos(
                    query = query,
                    hideCompleted = hideCompleted,
                    sorting = sorting
                )
            }.collectLatest { todos ->
                this@TodoListViewModel.todos = todos.toImmutableList()
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onReviewDismiss() {
        reviewShown = false
    }

    fun onReviewDeny() {
        review.deny()
    }

    fun onReviewLater() {
        review.remindLater()
    }

    fun onReviewConfirm() {
        viewModelScope.launch {
            _event.send(TodoListEvent.Review)
        }
    }

    fun onReview(activity: Activity?) {
        if (activity == null) {
            Timber.e("onReview(): activity is null! Aborted the operation.")
            return
        }

        review.review(activity)
    }

    fun onUpdateCheck() {
        update.monitor()
    }

    fun onUpdateDownload(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        update.downloadAppUpdate(launcher)
    }

    fun onUpdateAvailableDismiss() {
        updateAvailableType = UpdateManager.Type.None
    }

    fun onUpdateAvailableConfirm() {
        viewModelScope.launch {
            _event.send(TodoListEvent.DownloadAppUpdate)
        }
    }

    fun onUpdateDownloadedDismiss() {
        updateDownloadedShown = false
    }

    fun onUpdateInstall() {
        update.installAppUpdate()
    }

    fun onQueryChange(value: String) {
        viewModelScope.launch {
            _query.emit(value)
        }
    }

    fun onTodoChecked(
        todo: TodoModel,
        checked: Boolean
    ) {
        viewModelScope.launch {
            repository.updateTodo(
                todo.copy(
                    completed = checked
                )
            )
        }
    }

    fun onTodoDelete(todo: TodoModel) {
        viewModelScope.launch {
            deletedTodo = todo
            repository.deleteTodo(todo)
            _event.send(TodoListEvent.TodoDeleted)
        }
    }

    fun onUndoDeletedTodo() {
        viewModelScope.launch {
            deletedTodo?.let { todo ->
                repository.insertTodo(
                    todo.copy(id = 0)
                )
            }
            deletedTodo = null
        }
    }

    fun onSearchExpanded() {
        searchExpanded = true
    }

    fun onSearchCollapsed() {
        viewModelScope.launch {
            _query.emit("")
            searchExpanded = false
        }
    }

    fun onMenuExpanded() {
        menuExpanded = true
    }

    fun onMenuCollapsed() {
        menuExpanded = false
    }

    fun onSortExpanded() {
        sortExpanded = true
    }

    fun onSortCollapsed() {
        sortExpanded = false
    }

    fun onSort(sort: TodoSort) {
        viewModelScope.launch {
            preferences.upsertSorting(sort)
            onSortCollapsed()
        }
    }

    fun onHideCompletedChange() {
        viewModelScope.launch {
            preferences.upsertHideCompleted(!hideCompleted)
            onMenuCollapsed()
        }
    }

    fun onDeleteCompletedShow() {
        deleteCompletedShown = true
        onMenuCollapsed()
    }

    fun onDeleteCompletedDismiss() {
        deleteCompletedShown = false
    }

    fun onDeleteCompleted() {
        viewModelScope.launch {
            repository.deleteAllCompletedTodo()
            onDeleteCompletedDismiss()
        }
    }

    fun onDeleteAllShow() {
        deleteAllShown = true
        onMenuCollapsed()
    }

    fun onDeleteAllDismiss() {
        deleteAllShown = false
    }

    fun onDeleteAll() {
        viewModelScope.launch {
            repository.deleteAllTodo()
            onDeleteAllDismiss()
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // MISC
    ///////////////////////////////////////////////////////////////////////////

    enum class TodoListEvent {
        TodoDeleted,
        Review,
        DownloadAppUpdate;
    }
}