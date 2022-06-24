package xyz.teamgravity.todo.presentation.component.dialog

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.theme.backgroundDialogButton

@Composable
fun TodoAlertDialog(
    @StringRes title: Int,
    @StringRes message: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(id = title))
        },
        text = {
            Text(text = stringResource(id = message))
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.no),
                    color = MaterialTheme.colorScheme.backgroundDialogButton()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = stringResource(id = R.string.yes),
                    color = MaterialTheme.colorScheme.backgroundDialogButton()
                )
            }
        }
    )
}