package xyz.teamgravity.todo.presentation.component.checkbox

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun CheckableMenuItem(
    @StringRes title: Int,
    checked: Boolean,
    onCheckedChange: () -> Unit // checked is managed by viewmodel so we don't need that shit parameter
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = title))
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange() } // intentionally done!
        )
    }
}