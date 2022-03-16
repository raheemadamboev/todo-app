package xyz.teamgravity.todo.presentation.component.topbar

import androidx.annotation.StringRes
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.core.util.TodoSort

@Composable
fun TopBarSortMenu(
    expanded: Boolean,
    onExpand: () -> Unit,
    onDismiss: () -> Unit,
    onSort: (sort: TodoSort) -> Unit,
    menus: List<SortMenu> = sortMenuItems
) {
    TopBarIconButton(
        onClick = onExpand,
        icon = Icons.Default.Sort,
        contentDescription = R.string.cd_sort
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        menus.forEach { menu ->
            DropdownMenuItem(onClick = { onSort(menu.sort) }) {
                Text(text = stringResource(id = menu.title))
            }
        }
    }
}

data class SortMenu(
    @StringRes val title: Int,
    val sort: TodoSort
)

private val sortMenuItems = listOf(
    SortMenu(title = R.string.sort_by_name, TodoSort.BY_NAME),
    SortMenu(title = R.string.sort_by_date, TodoSort.BY_DATE)
)