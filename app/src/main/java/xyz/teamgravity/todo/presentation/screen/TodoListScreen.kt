package xyz.teamgravity.todo.presentation.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.presentation.screen.destinations.AddTodoScreenDestination
import xyz.teamgravity.todo.presentation.screen.destinations.EditTodoScreenDestination
import xyz.teamgravity.todo.presentation.theme.Black
import xyz.teamgravity.todo.presentation.theme.SuperLightWhite
import xyz.teamgravity.todo.presentation.theme.White
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
                title = { Text(text = stringResource(id = R.string.tasks)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(AddTodoScreenDestination)
                },
                elevation = FloatingActionButtonDefaults.elevation(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = stringResource(id = R.string.cd_task_add),
                    tint = White
                )
            }
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

@Composable
fun SwipeTodoCard(
    dismiss: DismissState = rememberDismissState(),
    todo: TodoModel,
    onTodoClick: (todo: TodoModel) -> Unit,
    onTodoCheckedChange: (todo: TodoModel, checked: Boolean) -> Unit,
    onTodoDismissed: (todo: TodoModel) -> Unit
) {

    if (dismiss.isDismissed(DismissDirection.EndToStart) || dismiss.isDismissed(DismissDirection.StartToEnd)) {
        LaunchedEffect(key1 = todo) {
            onTodoDismissed(todo)
        }
    }

    SwipeToDismiss(
        state = dismiss,
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(fraction = 0.35F) },
        background = {
            val direction = dismiss.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                targetValue = when (dismiss.targetValue) {
                    DismissValue.Default -> Color.LightGray
                    else -> Color.Red
                }
            )
            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }
            val tint by animateColorAsState(
                targetValue = when (dismiss.targetValue) {
                    DismissValue.Default -> Black
                    else -> White
                }
            )
            val scale by animateFloatAsState(if (dismiss.targetValue == DismissValue.Default) 0.75F else 1F)

            Box(
                contentAlignment = alignment,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Localized description",
                    tint = tint,
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = {
            TodoCard(
                todo = todo,
                onTodoClick = onTodoClick,
                onTodoCheckedChange = onTodoCheckedChange
            )
        },
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun TodoCard(
    todo: TodoModel,
    onTodoClick: (todo: TodoModel) -> Unit,
    onTodoCheckedChange: (todo: TodoModel, checked: Boolean) -> Unit
) {
    Card(
        onClick = { onTodoClick(todo) },
        shape = MaterialTheme.shapes.small,
        elevation = 4.dp,
        backgroundColor = White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Checkbox(
                checked = todo.completed,
                onCheckedChange = { checked ->
                    onTodoCheckedChange(todo, checked)
                }
            )
            Text(
                text = todo.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1F)
            )
            if (todo.important) {
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_warn),
                    contentDescription = stringResource(id = R.string.cd_task_important),
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                )
            }
        }

    }
}