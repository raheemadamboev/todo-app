package xyz.teamgravity.todo.presentation.component

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import xyz.teamgravity.todo.presentation.theme.Brown500
import xyz.teamgravity.todo.presentation.theme.White

@Composable
fun TodoFloatingActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String
) {
    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(10.dp),
        backgroundColor = Brown500
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = White
        )
    }
}