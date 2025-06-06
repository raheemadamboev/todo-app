package xyz.teamgravity.todo.presentation.screen.todo.edit

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.coresdkcompose.button.IconButtonPlain
import xyz.teamgravity.coresdkcompose.observe.ObserveEvent
import xyz.teamgravity.coresdkcompose.text.TextPlain
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.presentation.component.button.TodoFloatingActionButton
import xyz.teamgravity.todo.presentation.component.misc.TodoConfigure
import xyz.teamgravity.todo.presentation.component.topbar.TopBar
import xyz.teamgravity.todo.presentation.navigation.MainNavGraph

@Destination<MainNavGraph>(navArgs = TodoEditScreenArgs::class)
@Composable
fun TodoEditScreen(
    snackbar: SnackbarHostState = remember { SnackbarHostState() },
    navigator: DestinationsNavigator,
    viewmodel: TodoEditViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    ObserveEvent(
        flow = viewmodel.event,
        onEvent = { event ->
            when (event) {
                is TodoEditViewModel.TodoEditEvent.InvalidInput -> {
                    snackbar.showSnackbar(context.getString(event.message))
                }

                TodoEditViewModel.TodoEditEvent.TodoUpdated -> {
                    navigator.popBackStack()
                }
            }
        }
    )

    Scaffold(
        topBar = {
            TopBar(
                title = {
                    TextPlain(
                        id = R.string.edit_task
                    )
                },
                navigationIcon = {
                    IconButtonPlain(
                        onClick = {
                            dispatcher?.onBackPressed() ?: navigator.navigateUp()
                        },
                        icon = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = R.string.cd_back_button
                    )
                }
            )
        },
        floatingActionButton = {
            TodoFloatingActionButton(
                onClick = viewmodel::onUpdateTodo,
                icon = Icons.Rounded.Done,
                contentDescription = R.string.cd_done_button
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
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            Text(
                text = stringResource(id = R.string.x_created_timestamp, viewmodel.timestamp),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}

data class TodoEditScreenArgs(
    val todo: TodoModel
)