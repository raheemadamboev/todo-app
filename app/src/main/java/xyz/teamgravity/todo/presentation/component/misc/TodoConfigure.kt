package xyz.teamgravity.todo.presentation.component.misc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.teamgravity.todo.presentation.component.checkbox.TodoImportantCheckbox
import xyz.teamgravity.todo.presentation.component.textfield.TodoTextField

@Composable
fun TodoConfigure(
    name: String,
    onNameChange: (name: String) -> Unit,
    important: Boolean,
    onImportantChange: (important: Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TodoTextField(
            value = name,
            onValueChange = onNameChange
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        TodoImportantCheckbox(
            important = important,
            onImportantChange = onImportantChange
        )
    }
}