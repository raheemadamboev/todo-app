package xyz.teamgravity.todo.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.core.util.TodoSort
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.data.preferences.Preferences
import xyz.teamgravity.todo.data.repository.TodoRepository
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val preferences: Preferences
) : ViewModel() {

    private val _event = Channel<TodoListEvent> { }
    val event: Flow<TodoListEvent> = _event.receiveAsFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    var todos: List<TodoModel> by mutableStateOf(emptyList())
        private set

    var searchExpanded: Boolean by mutableStateOf(false)
        private set

    var menuExpanded: Boolean by mutableStateOf(false)
        private set

    var sortExpanded: Boolean by mutableStateOf(false)
        private set

    var hideCompleted: Boolean by mutableStateOf(false)
        private set

    var deleteCompletedDialog: Boolean by mutableStateOf(false)
        private set

    var deleteAllDialog: Boolean by mutableStateOf(false)
        private set

    private var deletedTodo: TodoModel? = null

    init {
        observeTodos()
    }

    private fun observeTodos() {
        viewModelScope.launch {
            combine(_query, preferences.preferences) { query, preferences ->
                Pair(query, preferences)
            }.flatMapLatest { (query, preferences) ->
                hideCompleted = preferences.hideCompleted
                repository.getTodos(query, preferences.hideCompleted, preferences.sort)
            }.collectLatest { todos ->
                this@TodoListViewModel.todos = todos
            }
        }
    }

    fun onQueryChange(value: String) {
        viewModelScope.launch { _query.emit(value) }
    }

    fun onTodoChecked(todo: TodoModel, checked: Boolean) {
        viewModelScope.launch { repository.updateTodoSync(todo.copy(completed = checked)) }
    }

    fun onTodoDelete(todo: TodoModel) {
        viewModelScope.launch {
            deletedTodo = todo
            repository.deleteTodoSync(todo)
            _event.send(TodoListEvent.TodoDeleted)
        }
    }

    fun onUndoDeletedTodo() {
        viewModelScope.launch { deletedTodo?.let { repository.insertTodoSync(it.copy(_id = 0)) } }
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
            preferences.updateTodoSort(sort = sort)
            onSortCollapsed()
        }
    }

    fun onHideCompletedChange() {
        viewModelScope.launch {
            preferences.updateHideCompleted(!hideCompleted)
            onMenuCollapsed()
        }
    }

    fun onDeleteCompletedDialogShow() {
        deleteCompletedDialog = true
        onMenuCollapsed()
    }

    fun onDeleteCompletedDialogDismiss() {
        deleteCompletedDialog = false
    }

    fun onDeleteCompleted() {
        viewModelScope.launch {
            repository.deleteAllCompletedTodo()
            onDeleteCompletedDialogDismiss()
        }
    }

    fun onDeleteAllDialogShow() {
        deleteAllDialog = true
        onMenuCollapsed()
    }

    fun onDeleteAllDialogDismiss() {
        deleteAllDialog = false
    }

    fun onDeleteAll() {
        viewModelScope.launch {
            repository.deleteAllTodo()
            onDeleteAllDialogDismiss()
        }
    }

    sealed class TodoListEvent {
        object TodoDeleted : TodoListEvent()
    }
}