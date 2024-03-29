package xyz.teamgravity.todo.presentation.screen.todo.list

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
import xyz.teamgravity.todo.data.local.preferences.Preferences
import xyz.teamgravity.todo.data.local.preferences.TodoSort
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.data.repository.TodoRepository
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val preferences: Preferences
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

    var deleteCompletedShown: Boolean by mutableStateOf(false)
        private set

    var deleteAllShown: Boolean by mutableStateOf(false)
        private set

    private val _event = Channel<TodoListEvent>()
    val event: Flow<TodoListEvent> = _event.receiveAsFlow()

    private var deletedTodo: TodoModel? = null

    init {
        observe()
    }

    private fun observe() {
        observeQueryAndPreferences()
    }

    private fun observeQueryAndPreferences() {
        viewModelScope.launch {
            combine(query, preferences.preferences) { query, preferences ->
                Pair(query, preferences)
            }.flatMapLatest { (query, preferences) ->
                hideCompleted = preferences.hideCompleted
                repository.getTodos(
                    query = query,
                    hideCompleted = preferences.hideCompleted,
                    sort = preferences.sort
                )
            }.collectLatest { todos ->
                this@TodoListViewModel.todos = todos.toImmutableList()
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onQueryChange(value: String) {
        viewModelScope.launch {
            _query.emit(value)
        }
    }

    fun onTodoChecked(todo: TodoModel, checked: Boolean) {
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
            preferences.updateTodoSort(sort)
            onSortCollapsed()
        }
    }

    fun onHideCompletedChange() {
        viewModelScope.launch {
            preferences.updateHideCompleted(!hideCompleted)
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
        TodoDeleted;
    }
}