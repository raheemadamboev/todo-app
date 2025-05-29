package xyz.teamgravity.todo.presentation.component.checkbox

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import xyz.teamgravity.coresdkcompose.text.TextPlain
import xyz.teamgravity.todo.R

@Composable
fun TodoImportantCheckbox(
    important: Boolean,
    onImportantChange: (checked: Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.toggleable(
            value = important,
            role = Role.Checkbox,
            onValueChange = onImportantChange
        )
    ) {
        Checkbox(
            checked = important,
            onCheckedChange = onImportantChange
        )
        TextPlain(
            id = R.string.important_task
        )
        Spacer(
            modifier = Modifier.width(16.dp)
        )
    }
}