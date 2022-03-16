package xyz.teamgravity.todo.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.component.button.TodoFloatingActionButton
import xyz.teamgravity.todo.presentation.component.card.TodoSwipeCard
import xyz.teamgravity.todo.presentation.component.dialog.TodoAlertDialog
import xyz.teamgravity.todo.presentation.component.topbar.*
import xyz.teamgravity.todo.presentation.screen.destinations.AboutScreenDestination
import xyz.teamgravity.todo.presentation.screen.destinations.AddTodoScreenDestination
import xyz.teamgravity.todo.presentation.screen.destinations.EditTodoScreenDestination
import xyz.teamgravity.todo.presentation.theme.backgroundLayout
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
                        TopBarIconButton(
                            onClick = viewmodel::onSearchExpanded,
                            icon = Icons.Default.Search,
                            contentDescription = R.string.cd_search_button
                        )
                    }
                    TopBarSortMenu(
                        expanded = viewmodel.sortExpanded,
                        onExpand = viewmodel::onSortExpanded,
                        onDismiss = viewmodel::onSortCollapsed,
                        onSort = viewmodel::onSort
                    )
                    TopBarMoreMenu(
                        expanded = viewmodel.menuExpanded,
                        onExpand = viewmodel::onMenuExpanded,
                        onDismiss = viewmodel::onMenuCollapsed,
                        hideCompleted = viewmodel.hideCompleted,
                        onHideCompletedChange = viewmodel::onHideCompletedChange,
                        onDeleteCompletedClick = viewmodel::onDeleteCompletedDialogShow,
                        onDeleteAllClick = viewmodel::onDeleteAllDialogShow,
                        onAboutClick = {
                            navigator.navigate(AboutScreenDestination)
                            viewmodel.onMenuCollapsed()
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            TodoFloatingActionButton(
                onClick = { navigator.navigate(AddTodoScreenDestination) },
                icon = Icons.Default.Add,
                contentDescription = R.string.cd_task_add
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.backgroundLayout)
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
}