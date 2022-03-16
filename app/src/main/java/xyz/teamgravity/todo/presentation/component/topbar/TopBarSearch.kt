package xyz.teamgravity.todo.presentation.component.topbar

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.theme.Muli
import xyz.teamgravity.todo.presentation.theme.White

@Composable
fun TopBarSearch(
    query: String,
    onQueryChange: (query: String) -> Unit,
    onCancel: () -> Unit
) {
    TopBarIconButton(
        onClick = onCancel,
        icon = Icons.Default.ArrowBack,
        contentDescription = R.string.cd_back_button
    )
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                color = White.copy(alpha = 0.4F),
                fontFamily = Muli
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            cursorColor = White
        ),
        textStyle = TextStyle.Default.copy(
            color = White,
            fontFamily = Muli
        )
    )
}