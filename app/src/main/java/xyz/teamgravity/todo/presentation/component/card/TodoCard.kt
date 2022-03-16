package xyz.teamgravity.todo.presentation.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.presentation.theme.White

@Composable
fun TodoCard(
    todo: TodoModel,
    onTodoClick: (todo: TodoModel) -> Unit,
    onTodoCheckedChange: (todo: TodoModel, checked: Boolean) -> Unit
) {
    Card(
        onClick = { onTodoClick(todo) },
        shape = MaterialTheme.shapes.large,
        elevation = 8.dp,
        backgroundColor = White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
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
                textDecoration = if (todo.completed) TextDecoration.LineThrough else TextDecoration.None,
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
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}