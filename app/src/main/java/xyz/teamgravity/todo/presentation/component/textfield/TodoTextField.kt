package xyz.teamgravity.todo.presentation.component.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.theme.Muli

@Composable
fun TodoTextField(
    value: String,
    onValueChange: (value: String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = stringResource(id = R.string.task_name)) },
        colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
    )
}