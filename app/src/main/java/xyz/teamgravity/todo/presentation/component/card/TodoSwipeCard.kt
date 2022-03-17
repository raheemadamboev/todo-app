package xyz.teamgravity.todo.presentation.component.card

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.presentation.theme.Black
import xyz.teamgravity.todo.presentation.theme.White
import xyz.teamgravity.todo.presentation.theme.textError

@Composable
fun TodoSwipeCard(
    todo: TodoModel,
    onTodoClick: (todo: TodoModel) -> Unit,
    onTodoCheckedChange: (todo: TodoModel, checked: Boolean) -> Unit,
    onTodoDismissed: (todo: TodoModel) -> Unit,
    dismiss: DismissState = rememberDismissState()
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
                    else -> MaterialTheme.colors.textError
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
                    contentDescription = stringResource(id = R.string.cd_delete_button),
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
        }
    )
}