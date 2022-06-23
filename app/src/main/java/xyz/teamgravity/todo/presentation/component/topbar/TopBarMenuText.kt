package xyz.teamgravity.todo.presentation.component.topbar

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import xyz.teamgravity.todo.presentation.theme.Muli

@Composable
fun TopBarMenuText(
    @StringRes text: Int
) {
    Text(
        text = stringResource(id = text),
        fontFamily = Muli
    )
}