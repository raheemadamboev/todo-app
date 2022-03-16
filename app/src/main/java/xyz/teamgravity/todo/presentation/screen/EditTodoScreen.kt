package xyz.teamgravity.todo.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.presentation.component.button.TodoFloatingActionButton
import xyz.teamgravity.todo.presentation.component.checkbox.TodoImportantCheckbox
import xyz.teamgravity.todo.presentation.component.textfield.TodoTextField
import xyz.teamgravity.todo.presentation.component.topbar.TopBarTitle
import xyz.teamgravity.todo.presentation.theme.SuperLightWhite
import xyz.teamgravity.todo.presentation.viewmodel.EditTodoViewModel

@Destination(navArgsDelegate = EditScreenNavArgs::class)
@Composable
fun EditTodoScreen(
    viewmodel: EditTodoViewModel = hiltViewModel(),
    scaffold: ScaffoldState = rememberScaffoldState(),
    navigator: DestinationsNavigator
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = viewmodel.event) {
        viewmodel.event.collectLatest { event ->
            when (event) {
                is EditTodoViewModel.EditTodoEvent.InvalidInput -> {
                    scaffold.snackbarHostState.showSnackbar(message = context.getString(event.message))
                }

                EditTodoViewModel.EditTodoEvent.TodoUpdated -> {
                    navigator.popBackStack()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffold,
        topBar = {
            TopAppBar(
                title = { TopBarTitle(title = R.string.edit_task) },
                navigationIcon = {
                    IconButton(onClick = navigator::popBackStack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.cd_back_button)
                        )
                    }
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
        modifier = Modifier
            .fillMaxSize()
            .background(SuperLightWhite)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TodoTextField(
                value = viewmodel.name,
                onValueChange = viewmodel::onNameChange
            )
            Spacer(modifier = Modifier.height(8.dp))
            TodoImportantCheckbox(
                important = viewmodel.important,
                onImportantChange = viewmodel::onImportantChange
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.your_created_timestamp, viewmodel.timestamp),
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}

data class EditScreenNavArgs(
    val todo: TodoModel
)