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
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val repository: TodoRepository
) : ViewModel() {

    companion object {
        private const val TASK_NAME = "task_name"
        private const val TASK_IMPORTANT = "task_important"
    }

    private val _event = Channel<AddTodoEvent> { }
    val event: Flow<AddTodoEvent> = _event.receiveAsFlow()

    var name: String by mutableStateOf(handle.get<String>(TASK_NAME) ?: "")
        private set

    var important: Boolean by mutableStateOf(handle.get<Boolean>(TASK_IMPORTANT) ?: false)
        private set

    fun onNameChange(value: String) {
        name = value
        handle[TASK_NAME] = value
    }

    fun onImportantChange(value: Boolean) {
        important = value
        handle[TASK_IMPORTANT] = value
    }

    fun onSaveTodo() {
        viewModelScope.launch {
            if (name.isBlank()) {
                _event.send(AddTodoEvent.InvalidInput(R.string.error_name))
                return@launch
            }

            repository.insertTodoSync(
                TodoModel(
                    name = name,
                    important = important
                )
            )

            _event.send(AddTodoEvent.TodoAdded)
        }
    }

    sealed class AddTodoEvent {
        data class InvalidInput(@StringRes val message: Int) : AddTodoEvent()
        object TodoAdded : AddTodoEvent()
    }
}