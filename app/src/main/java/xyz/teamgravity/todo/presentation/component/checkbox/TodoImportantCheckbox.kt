package xyz.teamgravity.todo.presentation.component.checkbox

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.teamgravity.todo.R

@Composable
fun TodoImportantCheckbox(
    important: Boolean,
    onImportantChange: (checked: Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = important,
            onCheckedChange = onImportantChange
        )
        Text(
            text = stringResource(id = R.string.important_task)
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
    }
}