package xyz.teamgravity.todo.presentation.screen.todo.list

import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.destinations.AboutScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SupportScreenDestination
import com.ramcosta.composedestinations.generated.destinations.TodoAddScreenDestination
import com.ramcosta.composedestinations.generated.destinations.TodoEditScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.coresdkandroid.connect.ConnectUtil
import xyz.teamgravity.coresdkcompose.observe.ObserveEvent
import xyz.teamgravity.coresdkcompose.review.DialogReview
import xyz.teamgravity.coresdkcompose.update.DialogUpdateAvailable
import xyz.teamgravity.coresdkcompose.update.DialogUpdateDownloaded
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.core.util.Helper
import xyz.teamgravity.todo.presentation.component.button.TodoFloatingActionButton
import xyz.teamgravity.todo.presentation.component.card.CardTodo
import xyz.teamgravity.todo.presentation.component.dialog.TodoAlertDialog
import xyz.teamgravity.todo.presentation.component.text.TextPlain
import xyz.teamgravity.todo.presentation.component.topbar.TopBar
import xyz.teamgravity.todo.presentation.component.topbar.TopBarIconButton
import xyz.teamgravity.todo.presentation.component.topbar.TopBarMoreMenu
import xyz.teamgravity.todo.presentation.component.topbar.TopBarSearch
import xyz.teamgravity.todo.presentation.component.topbar.TopBarSortMenu
import xyz.teamgravity.todo.presentation.navigation.MainNavGraph

@Destination<MainNavGraph>(start = true)
@Composable
fun TodoListScreen(
    snackbar: SnackbarHostState = remember { SnackbarHostState() },
    navigator: DestinationsNavigator,
    viewmodel: TodoListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val todos = viewmodel.todos.collectAsLazyPagingItems()
    val query by viewmodel.query.collectAsStateWithLifecycle()
    val updateLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = {}
    )

    ObserveEvent(
        flow = viewmodel.event,
        onEvent = { event ->
            when (event) {
                TodoListViewModel.TodoListEvent.TodoDeleted -> {
                    val result = snackbar.showSnackbar(
                        message = context.getString(R.string.deleted_successfully),
                        actionLabel = context.getString(R.string.undo),
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) viewmodel.onUndoDeletedTodo()
                }

                TodoListViewModel.TodoListEvent.Review -> {
                    viewmodel.onReview(activity)
                }

                TodoListViewModel.TodoListEvent.DownloadAppUpdate -> {
                    viewmodel.onUpdateDownload(updateLauncher)
                }
            }
        }
    )

    LifecycleEventEffect(
        event = Lifecycle.Event.ON_RESUME,
        onEvent = viewmodel::onUpdateCheck
    )

    Scaffold(
        topBar = {
            TopBar(
                title = {
                    if (viewmodel.searchExpanded) {
                        TopBarSearch(
                            query = query,
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
                            icon = Icons.Rounded.Search,
                            contentDescription = R.string.cd_search_button
                        )
                    }
                    TopBarSortMenu(
                        expanded = viewmodel.sortExpanded,
                        sorting = viewmodel.sorting,
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
                            ConnectUtil.viewAppPlayStorePage(context)
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
                icon = Icons.Rounded.Add,
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
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                count = todos.itemCount,
                key = todos.itemKey(
                    key = { todo ->
                        todo.id
                    }
                ),
                contentType = todos.itemContentType()
            ) { index ->
                val todo = todos[index]
                if (todo != null) {
                    CardTodo(
                        todo = todo,
                        onClick = {
                            navigator.navigate(TodoEditScreenDestination(it))
                        },
                        onCheckedChange = viewmodel::onTodoChecked,
                        onDismiss = viewmodel::onTodoDelete
                    )
                }
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
        DialogReview(
            visible = viewmodel.reviewShown,
            onDismiss = viewmodel::onReviewDismiss,
            onDeny = viewmodel::onReviewDeny,
            onRemindLater = viewmodel::onReviewLater,
            onReview = viewmodel::onReviewConfirm
        )
        DialogUpdateAvailable(
            type = viewmodel.updateAvailableType,
            onDismiss = viewmodel::onUpdateAvailableDismiss,
            onConfirm = viewmodel::onUpdateAvailableConfirm
        )
        DialogUpdateDownloaded(
            visible = viewmodel.updateDownloadedShown,
            onDismiss = viewmodel::onUpdateDownloadedDismiss,
            onConfirm = viewmodel::onUpdateInstall
        )
    }
}