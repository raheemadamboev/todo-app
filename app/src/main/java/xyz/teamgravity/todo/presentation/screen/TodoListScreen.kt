package xyz.teamgravity.todo.presentation.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.core.util.TodoSort
import xyz.teamgravity.todo.presentation.component.CheckableMenuItem
import xyz.teamgravity.todo.presentation.component.TodoAlertDialog
import xyz.teamgravity.todo.presentation.component.TodoFloatingActionButton
import xyz.teamgravity.todo.presentation.component.card.TodoSwipeCard
import xyz.teamgravity.todo.presentation.component.topbar.TopBarSearch
import xyz.teamgravity.todo.presentation.component.topbar.TopBarTitle
import xyz.teamgravity.todo.presentation.screen.destinations.AboutScreenDestination
import xyz.teamgravity.todo.presentation.screen.destinations.AddTodoScreenDestination
import xyz.teamgravity.todo.presentation.screen.destinations.EditTodoScreenDestination
import xyz.teamgravity.todo.presentation.theme.SuperLightWhite
import xyz.teamgravity.todo.presentation.viewmodel.TodoListViewModel

@Destination(start = true)
@Composable
fun TodoListScreen(
    viewmodel: TodoListViewModel = hiltViewModel(),
    scaffold: ScaffoldState = rememberScaffoldState(),
    navigator: DestinationsNavigator
) {
    Scaffold(
        scaffoldState = scaffold,
        topBar = {
            TopAppBar(
                title = {
                    if (viewmodel.searchExpanded) {
                        TopBarSearch(
                            query = viewmodel.query.collectAsState().value,
                            onQueryChange = viewmodel::onQueryChange,
                            onCancel = viewmodel::onSearchCollapsed
                        )
                    } else {
                        TopBarTitle(title = R.string.tasks)
                    }
                },
                actions = {
                    if (!viewmodel.searchExpanded) {
                        IconButton(onClick = viewmodel::onSearchExpanded) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.cd_search_button)
                            )
                        }
                    }
                    IconButton(onClick = viewmodel::onSortExpanded) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = stringResource(id = R.string.cd_sort)
                        )
                    }
                    DropdownMenu(
                        expanded = viewmodel.sortExpanded,
                        onDismissRequest = viewmodel::onSortCollapsed
                    ) {
                        sortMenuItems.forEach { menu ->
                            DropdownMenuItem(
                                onClick = {
                                    viewmodel.onSort(sort = menu.sort)
                                }
                            ) {
                                Text(text = stringResource(id = menu.title))
                            }
                        }
                    }
                    IconButton(onClick = viewmodel::onMenuExpanded) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.cd_more_vertical)
                        )
                    }
                    DropdownMenu(
                        expanded = viewmodel.menuExpanded,
                        onDismissRequest = viewmodel::onMenuCollapsed
                    ) {
                        DropdownMenuItem(onClick = viewmodel::onHideCompletedChange) {
                            CheckableMenuItem(
                                title = stringResource(id = R.string.hide_completed),
                                checked = viewmodel.hideCompleted,
                                onCheckedChange = viewmodel::onHideCompletedChange
                            )
                        }
                        DropdownMenuItem(onClick = viewmodel::onDeleteCompletedDialogShow) {
                            Text(text = stringResource(id = R.string.delete_all_completed))
                        }
                        DropdownMenuItem(onClick = viewmodel::onDeleteAllDialogShow) {
                            Text(text = stringResource(id = R.string.delete_all_tasks))
                        }
                        DropdownMenuItem(
                            onClick = {
                                navigator.navigate(AboutScreenDestination)
                                viewmodel.onMenuCollapsed()
                            }
                        ) {
                            Text(text = stringResource(id = R.string.about_me))
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            TodoFloatingActionButton(
                onClick = {
                    navigator.navigate(AddTodoScreenDestination)
                },
                icon = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.cd_task_add)
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(SuperLightWhite)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                items = viewmodel.todos,
                key = { it._id }
            ) { todo ->
                TodoSwipeCard(
                    todo = todo,
                    onTodoClick = { navigator.navigate(EditTodoScreenDestination(todo = it)) },
                    onTodoCheckedChange = viewmodel::onTodoChecked,
                    onTodoDismissed = viewmodel::onTodoDelete
                )
            }
        }
        if (viewmodel.deleteCompletedDialog) {
            TodoAlertDialog(
                title = R.string.confirm_deletion,
                message = R.string.wanna_delete_completed,
                onDismiss = viewmodel::onDeleteCompletedDialogDismiss,
                onConfirm = viewmodel::onDeleteCompleted
            )
        }
        if (viewmodel.deleteAllDialog) {
            TodoAlertDialog(
                title = R.string.confirm_deletion,
                message = R.string.wanna_delete_all,
                onDismiss = viewmodel::onDeleteAllDialogDismiss,
                onConfirm = viewmodel::onDeleteAll
            )
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