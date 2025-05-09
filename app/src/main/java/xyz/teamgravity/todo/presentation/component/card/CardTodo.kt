package xyz.teamgravity.todo.presentation.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import xyz.teamgravity.coresdkcompose.swipe.SwipeToDelete
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.presentation.theme.Black
import xyz.teamgravity.todo.presentation.theme.White

@Composable
fun CardTodo(
    todo: TodoModel,
    onClick: (todo: TodoModel) -> Unit,
    onCheckedChange: (todo: TodoModel, checked: Boolean) -> Unit,
    onDismiss: (todo: TodoModel) -> Unit
) {
    SwipeToDelete(
        model = todo,
        onDelete = onDismiss,
        duration = 700,
        deleteIcon = Icons.Rounded.Delete,
        enableDeleteFromStartToEnd = true,
        enableDeleteFromEndToStart = true,
        positionalThreshold = 0.35F,
        inactiveBackgroundColor = Color.LightGray,
        activeBackgroundColor = MaterialTheme.colorScheme.error,
        inactiveIconTint = Black,
        activeIconTint = White
    ) {
        Card(
            onClick = {
                onClick(todo)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
            ) {
                Checkbox(
                    checked = todo.completed,
                    onCheckedChange = { checked ->
                        onCheckedChange(todo, checked)
                    }
                )
                Text(
                    text = todo.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (todo.completed) TextDecoration.LineThrough else TextDecoration.None,
                    modifier = Modifier.weight(1F)
                )
                if (todo.important) {
                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_warn),
                        contentDescription = stringResource(id = R.string.cd_task_important),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
            }
        }
    }
}