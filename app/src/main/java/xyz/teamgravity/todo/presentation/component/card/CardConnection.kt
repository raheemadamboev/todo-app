package xyz.teamgravity.todo.presentation.component.card

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.teamgravity.todo.presentation.theme.White

@Composable
fun CardConnection(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    @StringRes title: Int,
    @StringRes contentDescription: Int,
    fillMaxSize: Boolean,
    modifier: Modifier,
) {
    ElevatedCard(
        onClick = onClick,
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = run {
                val rowModifier = if (fillMaxSize) Modifier.fillMaxSize() else Modifier
                rowModifier.padding(16.dp)
            }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = stringResource(id = contentDescription),
                    tint = White,
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = stringResource(id = title),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1F)
            )
        }
    }
}