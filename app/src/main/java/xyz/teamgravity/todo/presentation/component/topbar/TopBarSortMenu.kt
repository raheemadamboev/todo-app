package xyz.teamgravity.todo.presentation.component.topbar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.teamgravity.coresdkcompose.button.IconButtonPlain
import xyz.teamgravity.coresdkcompose.menu.GDropdownMenuItem
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.core.constant.TodoSort

@Composable
fun TopBarSortMenu(
    expanded: Boolean,
    sorting: TodoSort?,
    onExpand: () -> Unit,
    onDismiss: () -> Unit,
    onSort: (sort: TodoSort) -> Unit,
    menus: ImmutableList<SortMenu> = SORT_MENUS
) {
    IconButtonPlain(
        onClick = onExpand,
        icon = Icons.AutoMirrored.Rounded.Sort,
        contentDescription = R.string.cd_sort
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        menus.forEach { menu ->
            GDropdownMenuItem(
                onDismiss = onDismiss,
                onClick = {
                    onSort(menu.sort)
                },
                label = menu.title,
                trailingIcon = {
                    if (sorting == menu.sort) {
                        Icon(
                            imageVector = Icons.Rounded.Done,
                            contentDescription = stringResource(R.string.cd_selected_sort, stringResource(menu.title))
                        )
                    }
                }
            )
        }
    }
}

data class SortMenu(
    @StringRes val title: Int,
    val sort: TodoSort
)

private val SORT_MENUS: ImmutableList<SortMenu> = persistentListOf(
    SortMenu(
        title = R.string.sort_by_name,
        sort = TodoSort.Name
    ),
    SortMenu(
        title = R.string.sort_by_date,
        sort = TodoSort.Date
    )
)