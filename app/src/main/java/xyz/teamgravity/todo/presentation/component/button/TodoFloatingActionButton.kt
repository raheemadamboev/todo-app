package xyz.teamgravity.todo.presentation.component.button

import androidx.annotation.StringRes
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.teamgravity.todo.presentation.theme.Brown500
import xyz.teamgravity.todo.presentation.theme.White
import xyz.teamgravity.todo.presentation.theme.textButton

@Composable
fun TodoFloatingActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    @StringRes contentDescription: Int
) {
    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(10.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id = contentDescription),
            tint = MaterialTheme.colors.textButton
        )
    }
}