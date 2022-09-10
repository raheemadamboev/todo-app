package xyz.teamgravity.todo.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.core.extension.exhaustive
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.presentation.component.button.TodoFloatingActionButton
import xyz.teamgravity.todo.presentation.component.misc.TodoConfigure
import xyz.teamgravity.todo.presentation.component.text.TextPlain
import xyz.teamgravity.todo.presentation.component.topbar.TopBar
import xyz.teamgravity.todo.presentation.component.topbar.TopBarIconButton
import xyz.teamgravity.todo.presentation.navigation.MainNavGraph
import xyz.teamgravity.todo.presentation.viewmodel.TodoEditViewModel

@MainNavGraph
@Destination(navArgsDelegate = EditTodoScreenNavArgs::class)
@Composable
fun EditTodoScreen(
    viewmodel: TodoEditViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val snackbar = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = viewmodel.event) {
        viewmodel.event.collectLatest { event ->
            when (event) {
                is TodoEditViewModel.EditTodoEvent.InvalidInput -> {
                    snackbar.showSnackbar(message = context.getString(event.message))
                }

                TodoEditViewModel.EditTodoEvent.TodoUpdated -> {
                    navigator.popBackStack()
                }
            }.exhaustive
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = { TextPlain(id = R.string.edit_task) },
                navigationIcon = {
                    TopBarIconButton(
                        onClick = navigator::popBackStack,
                        icon = Icons.Default.ArrowBack,
                        contentDescription = R.string.cd_back_button
                    )
                }
            )
        },
        floatingActionButton = {
            TodoFloatingActionButton(
                onClick = viewmodel::onUpdateTodo,
                icon = Icons.Default.Done,
                contentDescription = R.string.cd_done_button
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbar) { data ->
                Snackbar(snackbarData = data)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TodoConfigure(
                name = viewmodel.name,
                onNameChange = viewmodel::onNameChange,
                important = viewmodel.important,
                onImportantChange = viewmodel::onImportantChange
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.your_created_timestamp, viewmodel.timestamp),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}

data class EditTodoScreenNavArgs(
    val todo: TodoModel
)