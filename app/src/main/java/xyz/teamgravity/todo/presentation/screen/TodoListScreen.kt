package xyz.teamgravity.todo.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.component.button.TodoFloatingActionButton
import xyz.teamgravity.todo.presentation.component.card.TodoSwipeCard
import xyz.teamgravity.todo.presentation.component.dialog.TodoAlertDialog
import xyz.teamgravity.todo.presentation.component.text.TextPlain
import xyz.teamgravity.todo.presentation.component.topbar.TopBarIconButton
import xyz.teamgravity.todo.presentation.component.topbar.TopBarMoreMenu
import xyz.teamgravity.todo.presentation.component.topbar.TopBarSearch
import xyz.teamgravity.todo.presentation.component.topbar.TopBarSortMenu
import xyz.teamgravity.todo.presentation.screen.destinations.AboutScreenDestination
import xyz.teamgravity.todo.presentation.screen.destinations.AddTodoScreenDestination
import xyz.teamgravity.todo.presentation.screen.destinations.EditTodoScreenDestination
import xyz.teamgravity.todo.presentation.viewmodel.TodoListViewModel

@Destination(start = true)
@Composable
fun TodoListScreen(
    viewmodel: TodoListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val snackbar = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = viewmodel.event) {
        viewmodel.event.collectLatest { event ->
            when (event) {
                TodoListViewModel.TodoListEvent.TodoDeleted -> {
                    val result = snackbar.showSnackbar(
                        message = context.getString(R.string.deleted_successfully),
                        actionLabel = context.getString(R.string.undo)
                    )
                    if (result == SnackbarResult.ActionPerformed) viewmodel.onUndoDeletedTodo()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    if (viewmodel.searchExpanded) {
                        TopBarSearch(
                            query = viewmodel.query.collectAsState().value,
                            onQueryChange = viewmodel::onQueryChange,
                            onCancel = viewmodel::onSearchCollapsed
                        )
                    } else {
                        TextPlain(id = R.string.tasks)
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
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbar) { data ->
                Snackbar(snackbarData = data)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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