package xyz.teamgravity.todo.presentation.screen.todo.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.core.util.Helper
import xyz.teamgravity.todo.presentation.component.button.TodoFloatingActionButton
import xyz.teamgravity.todo.presentation.component.card.CardTodoSwipe
import xyz.teamgravity.todo.presentation.component.dialog.TodoAlertDialog
import xyz.teamgravity.todo.presentation.component.misc.ObserveEvent
import xyz.teamgravity.todo.presentation.component.text.TextPlain
import xyz.teamgravity.todo.presentation.component.topbar.TopBar
import xyz.teamgravity.todo.presentation.component.topbar.TopBarIconButton
import xyz.teamgravity.todo.presentation.component.topbar.TopBarMoreMenu
import xyz.teamgravity.todo.presentation.component.topbar.TopBarSearch
import xyz.teamgravity.todo.presentation.component.topbar.TopBarSortMenu
import xyz.teamgravity.todo.presentation.navigation.MainNavGraph
import xyz.teamgravity.todo.presentation.screen.destinations.AboutScreenDestination
import xyz.teamgravity.todo.presentation.screen.destinations.SupportScreenDestination
import xyz.teamgravity.todo.presentation.screen.destinations.TodoAddScreenDestination
import xyz.teamgravity.todo.presentation.screen.destinations.TodoEditScreenDestination

@MainNavGraph(start = true)
@Destination
@Composable
fun TodoListScreen(
    snackbar: SnackbarHostState = remember { SnackbarHostState() },
    navigator: DestinationsNavigator,
    viewmodel: TodoListViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    ObserveEvent(
        flow = viewmodel.event,
        onEvent = { event ->
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
    )

    Scaffold(
        topBar = {
            TopBar(
                title = {
                    if (viewmodel.searchExpanded) {
                        TopBarSearch(
                            query = viewmodel.query.collectAsState().value,
                            onQueryChange = viewmodel::onQueryChange,
                            onCancel = viewmodel::onSearchCollapsed
                        )
                    } else {
                        TextPlain(
                            id = R.string.tasks
                        )
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
                        onDeleteCompletedClick = viewmodel::onDeleteCompletedShow,
                        onDeleteAllClick = viewmodel::onDeleteAllShow,
                        onSupportClick = {
                            navigator.navigate(SupportScreenDestination)
                            viewmodel.onMenuCollapsed()
                        },
                        onShareClick = {
                            Helper.shareApp(context)
                            viewmodel.onMenuCollapsed()
                        },
                        onRateClick = {
                            Helper.rateApp(context)
                            viewmodel.onMenuCollapsed()
                        },
                        onSourceCodeClick = {
                            Helper.viewSourceCode(context)
                            viewmodel.onMenuCollapsed()
                        },
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
                onClick = {
                    navigator.navigate(TodoAddScreenDestination)
                },
                icon = Icons.Default.Add,
                contentDescription = R.string.cd_task_add
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbar
            ) { data ->
                Snackbar(
                    snackbarData = data
                )
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = viewmodel.todos,
                key = { it.id }
            ) { todo ->
                CardTodoSwipe(
                    todo = todo,
                    onTodoClick = {
                        navigator.navigate(TodoEditScreenDestination(it))
                    },
                    onTodoCheckedChange = viewmodel::onTodoChecked,
                    onTodoDismissed = viewmodel::onTodoDelete
                )
            }
        }
        if (viewmodel.deleteCompletedShown) {
            TodoAlertDialog(
                title = R.string.confirm_deletion,
                message = R.string.wanna_delete_completed,
                onDismiss = viewmodel::onDeleteCompletedDismiss,
                onConfirm = viewmodel::onDeleteCompleted
            )
        }
        if (viewmodel.deleteAllShown) {
            TodoAlertDialog(
                title = R.string.confirm_deletion,
                message = R.string.wanna_delete_all,
                onDismiss = viewmodel::onDeleteAllDismiss,
                onConfirm = viewmodel::onDeleteAll
            )
        }
    }
}