package xyz.teamgravity.todo.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.theme.White
import xyz.teamgravity.todo.presentation.viewmodel.AddTodoViewModel

@Destination
@Composable
fun AddTodoScreen(
    viewmodel: AddTodoViewModel = hiltViewModel(),
    scaffold: ScaffoldState = rememberScaffoldState(),
    navigator: DestinationsNavigator
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = viewmodel.event) {
        viewmodel.event.collectLatest { event ->
            when (event) {
                is AddTodoViewModel.AddTodoEvent.InvalidInput -> {
                    scaffold.snackbarHostState.showSnackbar(message = context.getString(event.message))
                }

                AddTodoViewModel.AddTodoEvent.TodoAdded -> {
                    navigator.popBackStack()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffold,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.new_task)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.cd_back_button)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewmodel::onSaveTodo) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(id = R.string.cd_done_button),
                    tint = White
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        ) {
            OutlinedTextField(
                value = viewmodel.name,
                onValueChange = viewmodel::onNameChange,
                placeholder = { Text(text = stringResource(id = R.string.task_name)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = viewmodel.important,
                    onCheckedChange = viewmodel::onImportantChange
                )
                Text(text = stringResource(id = R.string.important_task))
            }
        }
    }
}