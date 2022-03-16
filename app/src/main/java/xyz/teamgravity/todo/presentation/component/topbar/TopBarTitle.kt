package xyz.teamgravity.todo.presentation.component.topbar

import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.theme.Muli

@Composable
fun TopBarTitle(
    @StringRes title: Int
) {
    Text(
        text = stringResource(id = title),
        fontFamily = Muli
    )
}