package xyz.teamgravity.todo.presentation.viewmodel

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
import xyz.teamgravity.todo.injection.name.FullTimeFormatter
import xyz.teamgravity.todo.presentation.screen.destinations.EditTodoScreenDestination
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val repository: TodoRepository,
    @FullTimeFormatter private val formatter: SimpleDateFormat
) : ViewModel() {

    companion object {
        private const val TODO = "todo"
        private const val TODO_NAME = "todo_name"
        private const val TODO_IMPORTANT = "todo_important"
    }

    private val args = EditTodoScreenDestination.argsFrom(handle)

    private val _event = Channel<EditTodoEvent> { }
    val event: Flow<EditTodoEvent> = _event.receiveAsFlow()

    private val todo: TodoModel by mutableStateOf(handle.get<TodoModel>(TODO) ?: args.todo)

    var name: String by mutableStateOf(handle.get<String>(TODO_NAME) ?: todo.name)
        private set

    var important: Boolean by mutableStateOf(handle.get<Boolean>(TODO_IMPORTANT) ?: todo.important)
        private set

    val timestamp: String by mutableStateOf(formatter.format(todo.timestamp))

    fun onNameChange(value: String) {
        name = value
        handle[TODO_NAME] = value
    }

    fun onImportantChange(value: Boolean) {
        important = value
        handle[TODO_IMPORTANT] = value
    }

    fun onUpdateTodo() {
        viewModelScope.launch {
            if (name.isBlank()) {
                _event.send(EditTodoEvent.InvalidInput(message = R.string.error_name))
                return@launch
            }

            repository.updateTodoSync(
                todo.copy(
                    name = name,
                    important = important
                )
            )

            _event.send(EditTodoEvent.TodoUpdated)
        }
    }

    sealed class EditTodoEvent {
        data class InvalidInput(@StringRes val message: Int) : EditTodoEvent()
        object TodoUpdated : EditTodoEvent()
    }
}