package xyz.teamgravity.todo.presentation.screen.todo.add

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.data.repository.TodoRepository
import javax.inject.Inject

@HiltViewModel
class TodoAddViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val repository: TodoRepository
) : ViewModel() {

    private companion object {
        const val TODO_NAME = "todo_name"
        const val DEFAULT_TODO_NAME = ""

        const val TODO_IMPORTANT = "todo_important"
        const val DEFAULT_TODO_IMPORTANT = false
    }

    var name: String by mutableStateOf(handle.get<String>(TODO_NAME) ?: DEFAULT_TODO_NAME)
        private set

    var important: Boolean by mutableStateOf(handle.get<Boolean>(TODO_IMPORTANT) ?: DEFAULT_TODO_IMPORTANT)
        private set

    private val _event = Channel<TodoAddEvent>()
    val event: Flow<TodoAddEvent> = _event.receiveAsFlow()

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onNameChange(value: String) {
        name = value
        handle[TODO_NAME] = value
    }

    fun onImportantChange(value: Boolean) {
        important = value
        handle[TODO_IMPORTANT] = value
    }

    fun onSaveTodo() {
        viewModelScope.launch {
            if (name.isBlank()) {
                _event.send(TodoAddEvent.InvalidInput(R.string.error_name))
                return@launch
            }

            repository.insertTodo(
                TodoModel(
                    name = name,
                    important = important
                )
            )

            _event.send(TodoAddEvent.TodoAdded)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // MISC
    ///////////////////////////////////////////////////////////////////////////

    sealed interface TodoAddEvent {
        data class InvalidInput(@StringRes val message: Int) : TodoAddEvent
        data object TodoAdded : TodoAddEvent
    }
}