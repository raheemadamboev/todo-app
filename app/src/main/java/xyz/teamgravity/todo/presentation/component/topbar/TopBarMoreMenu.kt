package xyz.teamgravity.todo.presentation.component.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.component.text.TextPlain

@Composable
fun TopBarMoreMenu(
    expanded: Boolean,
    onExpand: () -> Unit,
    onDismiss: () -> Unit,
    hideCompleted: Boolean,
    onHideCompletedChange: () -> Unit,
    onDeleteCompletedClick: () -> Unit,
    onDeleteAllClick: () -> Unit,
    onSupportClick: () -> Unit,
    onShareClick: () -> Unit,
    onRateClick: () -> Unit,
    onSourceCodeClick: () -> Unit,
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
        DropdownMenuItem(
            text = { TextPlain(id = R.string.hide_completed) },
            onClick = onHideCompletedChange,
            trailingIcon = {
                if (hideCompleted) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = stringResource(id = R.string.cd_hide_completed)
                    )
                }
            },
            leadingIcon = {
                TopBarIcon(
                    icon = R.drawable.ic_hide,
                    contentDescription = R.string.hide_completed
                )
            }
        )
        DropdownMenuItem(
            text = { TextPlain(id = R.string.delete_all_completed) },
            onClick = onDeleteCompletedClick,
            leadingIcon = {
                TopBarIcon(
                    icon = R.drawable.ic_delete,
                    contentDescription = R.string.delete_all_completed
                )
            }
        )
        DropdownMenuItem(
            text = { TextPlain(id = R.string.delete_all_tasks) },
            onClick = onDeleteAllClick,
            leadingIcon = {
                TopBarIcon(
                    icon = R.drawable.ic_delete,
                    contentDescription = R.string.delete_all_tasks
                )
            }
        )
        DropdownMenuItem(
            text = { TextPlain(id = R.string.support) },
            onClick = onSupportClick,
            leadingIcon = {
                TopBarIcon(
                    icon = R.drawable.ic_customer_service,
                    contentDescription = R.string.support
                )
            }
        )
        DropdownMenuItem(
            text = { TextPlain(id = R.string.share) },
            onClick = onShareClick,
            leadingIcon = {
                TopBarIcon(
                    icon = R.drawable.ic_share,
                    contentDescription = R.string.share
                )
            }
        )
        DropdownMenuItem(
            text = { TextPlain(id = R.string.rate) },
            onClick = onRateClick,
            leadingIcon = {
                TopBarIcon(
                    icon = R.drawable.ic_star,
                    contentDescription = R.string.rate
                )
            }
        )
        DropdownMenuItem(
            text = { TextPlain(id = R.string.source_code) },
            onClick = onSourceCodeClick,
            leadingIcon = {
                TopBarIcon(
                    icon = R.drawable.ic_github,
                    contentDescription = R.string.source_code,
                )
            }
        )
        DropdownMenuItem(
            text = { TextPlain(id = R.string.about_me) },
            onClick = onAboutClick,
            leadingIcon = {
                TopBarIcon(
                    icon = R.drawable.ic_info,
                    contentDescription = R.string.about_me
                )
            }
        )
    }
}