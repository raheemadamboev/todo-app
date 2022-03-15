package xyz.teamgravity.todo.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CheckableMenuItem(
    title: String,
    checked: Boolean,
    onCheckedChange: () -> Unit // checked is managed by viewmodel so we don't need that shit parameter
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = title)
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange() } // intentionally done!
        )
    }
}