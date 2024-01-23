package xyz.teamgravity.todo.presentation.component.card

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
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

@Composable
fun CardTodoSwipe(
    todo: TodoModel,
    onTodoClick: (todo: TodoModel) -> Unit,
    onTodoCheckedChange: (todo: TodoModel, checked: Boolean) -> Unit,
    onTodoDismissed: (todo: TodoModel) -> Unit,
    dismiss: DismissState = rememberDismissState(
        positionalThreshold = { distance ->
            distance * 0.35F
        }
    )
) {
    if (dismiss.isDismissed(DismissDirection.StartToEnd) || dismiss.isDismissed(DismissDirection.EndToStart)) {
        LaunchedEffect(key1 = todo) {
            onTodoDismissed(todo)
        }
    }

    SwipeToDismiss(
        state = dismiss,
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        background = {
            val direction = dismiss.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                targetValue = when (dismiss.targetValue) {
                    DismissValue.Default -> Color.LightGray
                    else -> MaterialTheme.colorScheme.error
                },
                label = "color"
            )
            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }
            val tint by animateColorAsState(
                targetValue = when (dismiss.targetValue) {
                    DismissValue.Default -> Black
                    else -> White
                },
                label = "tint"
            )
            val scale by animateFloatAsState(
                targetValue = if (dismiss.targetValue == DismissValue.Default) 0.75F else 1F,
                label = "scale"
            )

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
            CardTodo(
                todo = todo,
                onTodoClick = onTodoClick,
                onTodoCheckedChange = onTodoCheckedChange
            )
        }
    )
}