package xyz.teamgravity.todo.presentation.component.button

import androidx.annotation.StringRes
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun TodoFloatingActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    @StringRes contentDescription: Int
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id = contentDescription),
        )
    }
}