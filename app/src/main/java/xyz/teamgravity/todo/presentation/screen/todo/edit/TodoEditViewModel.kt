package xyz.teamgravity.todo.presentation.screen.todo.edit

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
import xyz.teamgravity.todo.data.repository.TodoRepository
import xyz.teamgravity.todo.injection.name.FullTimeFormatter
import xyz.teamgravity.todo.presentation.screen.destinations.TodoEditScreenDestination
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val repository: TodoRepository,
    @FullTimeFormatter private val formatter: SimpleDateFormat
) : ViewModel() {

    private companion object {
        const val TODO_NAME = "todo_name"
        const val TODO_IMPORTANT = "todo_important"
    }

    private val args: TodoEditScreenArgs = TodoEditScreenDestination.argsFrom(handle)

    var name: String by mutableStateOf(handle.get<String>(TODO_NAME) ?: args.todo.name)
        private set

    var important: Boolean by mutableStateOf(handle.get<Boolean>(TODO_IMPORTANT) ?: args.todo.important)
        private set

    val timestamp: String by mutableStateOf(formatter.format(args.todo.timestamp))

    private val _event = Channel<TodoEditEvent>()
    val event: Flow<TodoEditEvent> = _event.receiveAsFlow()

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

    fun onUpdateTodo() {
        viewModelScope.launch {
            if (name.isBlank()) {
                _event.send(TodoEditEvent.InvalidInput(R.string.error_name))
                return@launch
            }

            repository.updateTodo(
                args.todo.copy(
                    name = name,
                    important = important
                )
            )

            _event.send(TodoEditEvent.TodoUpdated)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // MISC
    ///////////////////////////////////////////////////////////////////////////

    sealed interface TodoEditEvent {
        data class InvalidInput(@StringRes val message: Int) : TodoEditEvent
        data object TodoUpdated : TodoEditEvent
    }
}