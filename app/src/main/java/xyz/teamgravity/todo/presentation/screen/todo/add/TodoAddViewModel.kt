package xyz.teamgravity.todo.presentation.screen.todo.add

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    private val repository: TodoRepository
) : ViewModel() {

    var name: String by mutableStateOf("")
        private set

    var important: Boolean by mutableStateOf(false)
        private set

    private val _event = Channel<TodoAddEvent>()
    val event: Flow<TodoAddEvent> = _event.receiveAsFlow()

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onNameChange(value: String) {
        name = value
    }

    fun onImportantChange(value: Boolean) {
        important = value
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