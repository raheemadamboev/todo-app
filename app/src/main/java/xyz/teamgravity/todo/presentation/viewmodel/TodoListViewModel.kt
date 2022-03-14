package xyz.teamgravity.todo.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.core.util.Preferences
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

    var todos by mutableStateOf(emptyList<TodoModel>())
        private set

    init {
        observeTodos()
    }

    private fun observeTodos() {
        viewModelScope.launch {
            combine(_query, preferences.preferences) { query, preferences ->
                Pair(query, preferences)
            }.flatMapLatest { (query, preferences) ->
                repository.getTodos(query, preferences.hideCompleted, preferences.sort)
            }.collectLatest { todos ->
                println("raheem: $todos")
                this@TodoListViewModel.todos = todos
            }
        }
    }

    fun onTodoChecked(todo: TodoModel, checked: Boolean) {
        viewModelScope.launch {
            repository.updateTodoSync(todo.copy(completed = checked))
        }
    }

    fun onTodoDelete(todo: TodoModel) {
        viewModelScope.launch {
            repository.deleteTodoSync(todo)
        }
    }
}