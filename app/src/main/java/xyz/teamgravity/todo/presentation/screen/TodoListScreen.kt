package xyz.teamgravity.todo.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.component.SwipeTodoCard
import xyz.teamgravity.todo.presentation.component.TodoFloatingActionButton
import xyz.teamgravity.todo.presentation.component.TopAppBarTitle
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
        topBar = { TopAppBar(title = { TopAppBarTitle(title = stringResource(id = R.string.tasks)) }) },
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
                SwipeTodoCard(
                    todo = todo,
                    onTodoClick = { navigator.navigate(EditTodoScreenDestination(todo = it)) },
                    onTodoCheckedChange = viewmodel::onTodoChecked,
                    onTodoDismissed = viewmodel::onTodoDelete
                )
            }
        }
    }
}