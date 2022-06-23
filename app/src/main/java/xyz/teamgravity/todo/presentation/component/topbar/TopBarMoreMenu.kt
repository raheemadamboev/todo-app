package xyz.teamgravity.todo.presentation.component.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import xyz.teamgravity.todo.R

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
        /* DropdownMenuItem(onClick = onHideCompletedChange) {
             CheckableMenuItem(
                 title = R.string.hide_completed,
                 checked = hideCompleted,
                 onCheckedChange = onHideCompletedChange
             )
         }

         */
        DropdownMenuItem(
            onClick = onDeleteCompletedClick,
            text = { TopBarMenuText(text = R.string.delete_all_completed) }
        )
        DropdownMenuItem(
            onClick = onDeleteAllClick,
            text = { TopBarMenuText(text = R.string.delete_all_tasks) }
        )
        DropdownMenuItem(
            onClick = onAboutClick,
            text = { TopBarMenuText(text = R.string.about_me) }
        )
    }
}