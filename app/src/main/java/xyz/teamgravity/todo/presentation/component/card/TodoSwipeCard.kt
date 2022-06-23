package xyz.teamgravity.todo.presentation.component.card

import androidx.compose.runtime.Composable
import xyz.teamgravity.todo.data.model.TodoModel

@Composable
fun TodoSwipeCard(
    todo: TodoModel,
    onTodoClick: (todo: TodoModel) -> Unit,
    onTodoCheckedChange: (todo: TodoModel, checked: Boolean) -> Unit,
    onTodoDismissed: (todo: TodoModel) -> Unit,
    //dismiss: DismissState = rememberDismissState()
) {
    TodoCard(
        todo = todo,
        onTodoClick = onTodoClick,
        onTodoCheckedChange = onTodoCheckedChange
    )
}