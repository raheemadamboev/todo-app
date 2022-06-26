package xyz.teamgravity.todo.presentation.component.topbar

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun TopBarIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    @StringRes contentDescription: Int,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id = contentDescription),
            tint = tint
        )
    }
}