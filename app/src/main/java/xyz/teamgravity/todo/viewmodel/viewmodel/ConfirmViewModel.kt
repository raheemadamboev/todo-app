package xyz.teamgravity.todo.viewmodel.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.fragment.dialog.ConfirmDialog
import xyz.teamgravity.todo.injection.ApplicationScope
import xyz.teamgravity.todo.data.local.TodoDao

class ConfirmViewModel @ViewModelInject constructor(
    private val dao: TodoDao,
    @ApplicationScope private val applicationScope: CoroutineScope,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {
    companion object {
        const val HEADER_TEXT = "header"
        const val BODY_TEXT = "body"
        const val POSITIVE_BUTTON_TEXT = "positive_button"
        const val NEGATIVE_BUTTON_TEXT = "negative_button"
        const val CODE = "code"
    }

    var headerText = state.get<String>(HEADER_TEXT) ?: ""
        set(value) {
            field = value
            state.set(HEADER_TEXT, value)
        }

    var bodyText = state.get<String>(BODY_TEXT) ?: ""
        set(value) {
            field = value
            state.set(BODY_TEXT, value)
        }

    var positiveButtonText = state.get<String>(POSITIVE_BUTTON_TEXT) ?: ""
        set(value) {
            field = value
            state.set(POSITIVE_BUTTON_TEXT, value)
        }

    var negativeButtonText = state.get<String>(NEGATIVE_BUTTON_TEXT) ?: ""
        set(value) {
            field = value
            state.set(NEGATIVE_BUTTON_TEXT, value)
        }

    var code = state.get<Int>(CODE) ?: 0
        set(value) {
            field = value
            state.set(CODE, value)
        }

    /**
     * Events
     */

    /**
     * Confirm dialog positive button click
     */
    fun onPositiveButtonClick(code: Int) = applicationScope.launch {
        when (code) {
            ConfirmDialog.DELETE_COMPLETED_TASK -> dao.deleteAllCompletedTodo()
            ConfirmDialog.DELETE_ALL_TASKS -> dao.deleteAllTodo()
        }
    }
}