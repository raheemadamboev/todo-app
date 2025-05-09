package xyz.teamgravity.todo.presentation.component.topbar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.core.constant.TodoSort
import xyz.teamgravity.todo.presentation.component.text.TextPlain

@Composable
fun TopBarSortMenu(
    expanded: Boolean,
    onExpand: () -> Unit,
    onDismiss: () -> Unit,
    onSort: (sort: TodoSort) -> Unit,
    menus: ImmutableList<SortMenu> = SORT_MENUS
) {
    TopBarIconButton(
        onClick = onExpand,
        icon = Icons.AutoMirrored.Filled.Sort,
        contentDescription = R.string.cd_sort
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        menus.forEach { menu ->
            DropdownMenuItem(
                onClick = {
                    onSort(menu.sort)
                },
                text = {
                    TextPlain(
                        id = menu.title
                    )
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