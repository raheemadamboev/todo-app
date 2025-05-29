package xyz.teamgravity.todo.presentation.component.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import xyz.teamgravity.coresdkandroid.android.BuildUtil
import xyz.teamgravity.coresdkcompose.button.IconButtonPlain
import xyz.teamgravity.coresdkcompose.image.IconPlain
import xyz.teamgravity.coresdkcompose.menu.GDropdownMenuItem
import xyz.teamgravity.todo.R

@Composable
fun TopBarMoreMenu(
    expanded: Boolean,
    onExpand: () -> Unit,
    onDismiss: () -> Unit,
    hideCompleted: Boolean,
    onHideCompletedChange: () -> Unit,
    deleteCompletedEnabled: Boolean,
    onDeleteCompletedClick: () -> Unit,
    deleteAllEnabled: Boolean,
    onDeleteAllClick: () -> Unit,
    onLanguageClick: () -> Unit,
    onSupportClick: () -> Unit,
    onShareClick: () -> Unit,
    onRateClick: () -> Unit,
    onSourceCodeClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    IconButtonPlain(
        onClick = onExpand,
        icon = Icons.Rounded.MoreVert,
        contentDescription = R.string.cd_more_vertical
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        GDropdownMenuItem(
            onDismiss = onDismiss,
            onClick = onHideCompletedChange,
            icon = R.drawable.ic_hide,
            label = R.string.hide_completed,
            trailingIcon = {
                if (hideCompleted) {
                    IconPlain(
                        icon = Icons.Rounded.Done,
                        contentDescription = R.string.cd_hide_completed
                    )
                }
            }
        )
        GDropdownMenuItem(
            onDismiss = onDismiss,
            onClick = onDeleteCompletedClick,
            icon = R.drawable.ic_delete,
            label = R.string.delete_all_completed,
            enabled = deleteCompletedEnabled
        )
        GDropdownMenuItem(
            onDismiss = onDismiss,
            onClick = onDeleteAllClick,
            icon = R.drawable.ic_delete,
            label = R.string.delete_all_tasks,
            enabled = deleteAllEnabled
        )
        if (BuildUtil.atLeastTiramisu()) {
            GDropdownMenuItem(
                onDismiss = onDismiss,
                onClick = onLanguageClick,
                icon = R.drawable.ic_language,
                label = R.string.change_language
            )
        }
        GDropdownMenuItem(
            onDismiss = onDismiss,
            onClick = onSupportClick,
            icon = R.drawable.ic_customer_service,
            label = R.string.support
        )
        GDropdownMenuItem(
            onDismiss = onDismiss,
            onClick = onShareClick,
            icon = R.drawable.ic_share,
            label = R.string.share
        )
        GDropdownMenuItem(
            onDismiss = onDismiss,
            onClick = onRateClick,
            icon = R.drawable.ic_star,
            label = R.string.rate
        )
        GDropdownMenuItem(
            onDismiss = onDismiss,
            onClick = onSourceCodeClick,
            icon = R.drawable.ic_github,
            label = R.string.source_code
        )
        GDropdownMenuItem(
            onDismiss = onDismiss,
            onClick = onAboutClick,
            icon = R.drawable.ic_info,
            label = R.string.about_me
        )
    }
}