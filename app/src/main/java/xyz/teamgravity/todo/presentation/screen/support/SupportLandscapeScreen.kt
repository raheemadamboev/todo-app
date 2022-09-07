package xyz.teamgravity.todo.presentation.screen.support

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.core.util.Helper
import xyz.teamgravity.todo.presentation.component.card.CardConnection
import xyz.teamgravity.todo.presentation.component.topbar.TopBarIconButton
import xyz.teamgravity.todo.presentation.theme.White

@Composable
fun SupportLandscapeScreen(
    onBackButtonClick: () -> Unit
) {

    val context = LocalContext.current

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (gradientC, connectT, bodyT) = createRefs()
        val (telegramB, emailB) = createRefs()
        val oneG = createGuidelineFromStart(0.5F)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(12.dp)
                .constrainAs(gradientC) {
                    width = Dimension.matchParent
                    top.linkTo(parent.top)
                },
        ) {
            TopBarIconButton(
                onClick = onBackButtonClick,
                icon = Icons.Default.ArrowBack,
                contentDescription = R.string.cd_back_button,
                tint = White
            )
            Text(
                text = stringResource(id = R.string.need_help),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = White,
                modifier = Modifier.weight(1F)
            )
            Spacer(modifier = Modifier.width(38.dp))
        }
        Text(
            text = stringResource(id = R.string.connect_us),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(connectT) {
                width = Dimension.matchParent
                linkTo(start = parent.start, end = parent.end, startMargin = 30.dp, endMargin = 30.dp)
                linkTo(top = gradientC.bottom, bottom = bodyT.top)
            }
        )
        Text(
            text = stringResource(id = R.string.connect_us_body),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.constrainAs(bodyT) {
                width = Dimension.matchParent
                height = Dimension.value(40.dp)
                linkTo(start = parent.start, end = parent.end, startMargin = 30.dp, endMargin = 30.dp)
                linkTo(top = connectT.bottom, bottom = telegramB.top)
            },
        )
        CardConnection(
            onClick = { Helper.connectViaTelegram(context) },
            icon = R.drawable.ic_telegram,
            title = R.string.via_telegram,
            contentDescription = R.string.cd_via_telegram,
            fillMaxSize = false,
            modifier = Modifier.constrainAs(telegramB) {
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
                linkTo(start = parent.start, end = oneG, startMargin = 16.dp, endMargin = 8.dp)
                linkTo(top = bodyT.bottom, bottom = parent.bottom)
            }
        )
        CardConnection(
            onClick = { Helper.connectViaEmail(context) },
            icon = R.drawable.ic_mail,
            title = R.string.via_email,
            contentDescription = R.string.cd_via_email,
            fillMaxSize = false,
            modifier = Modifier.constrainAs(emailB) {
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
                linkTo(start = oneG, end = parent.end, startMargin = 8.dp, endMargin = 16.dp)
                linkTo(top = bodyT.bottom, bottom = parent.bottom)
            }
        )
    }
}