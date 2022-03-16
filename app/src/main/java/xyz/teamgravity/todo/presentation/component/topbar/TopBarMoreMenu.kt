package xyz.teamgravity.todo.presentation.component.topbar

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.component.CheckableMenuItem

@Composable
fun TopBarMoreMenu(
    expanded: Boolean,
    onExpand: () -> Unit,
    onDismiss: () -> Unit,
    hideCompleted: Boolean,
    onHideCompletedChange: () -> Unit,
    onDeleteCompletedClick: () -> Unit,
    onDeleteAllClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    TopBarIconButton(
        onClick = onExpand,
        icon = Icons.Default.MoreVert,
        contentDescription = R.string.cd_more_vertical
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        DropdownMenuItem(onClick = onHideCompletedChange) {
            CheckableMenuItem(
                title = R.string.hide_completed,
                checked = hideCompleted,
                onCheckedChange = onHideCompletedChange
            )
        }
        DropdownMenuItem(onClick = onDeleteCompletedClick) {
            Text(text = stringResource(id = R.string.delete_all_completed))
        }
        DropdownMenuItem(onClick = onDeleteAllClick) {
            Text(text = stringResource(id = R.string.delete_all_tasks))
        }
        DropdownMenuItem(onClick = onAboutClick) {
            Text(text = stringResource(id = R.string.about_me))
        }
    }
}