package xyz.teamgravity.todo.presentation.screen.support

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.theme.Brown200
import xyz.teamgravity.todo.presentation.theme.Brown700
import xyz.teamgravity.todo.presentation.theme.White

@Composable
fun SupportPortraitScreen(
    onBackButtonClick: () -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (gradientC, backB, headerT, headerI, connectT, bodyT) = createRefs()
        val (telegramB, mailB) = createRefs()
        val oneG = createGuidelineFromTop(0.3F)
        val twoG = createGuidelineFromTop(0.5F)
        val threeG = createGuidelineFromTop(0.75F)

        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Brown700, Brown200)
                    )
                )
                .constrainAs(gradientC) {
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                    linkTo(top = parent.top, bottom = oneG)
                }
        )
        IconButton(
            onClick = onBackButtonClick,
            modifier = Modifier.constrainAs(backB) {
                width = Dimension.value(28.dp)
                height = Dimension.value(28.dp)
                start.linkTo(parent.start, 12.dp)
                top.linkTo(parent.top, 12.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.cd_back_button),
                tint = White,
                modifier = Modifier.size(28.dp)
            )
        }
        Text(
            text = stringResource(id = R.string.need_help),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = White,
            modifier = Modifier.constrainAs(headerT) {
                width = Dimension.fillToConstraints
                linkTo(start = backB.end, end = parent.end, endMargin = 38.dp)
                linkTo(top = backB.top, bottom = backB.bottom)
            }
        )
        Image(
            painter = painterResource(id = R.drawable.sticker_customer_service),
            contentDescription = stringResource(id = R.string.cd_user_support),
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
            modifier = Modifier.constrainAs(headerI) {
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
                linkTo(top = headerT.bottom, bottom = oneG, topMargin = 20.dp)
            }
        )
        Text(
            text = stringResource(id = R.string.connect_us),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(connectT) {
                width = Dimension.matchParent
                linkTo(start = parent.start, end = parent.end, startMargin = 30.dp, endMargin = 30.dp)
                linkTo(top = oneG, bottom = bodyT.top)
            }
        )
        Text(
            text = stringResource(id = R.string.connect_us_body),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(bodyT) {
                width = Dimension.matchParent
                linkTo(start = parent.start, end = parent.end, startMargin = 30.dp, endMargin = 30.dp)
                linkTo(top = connectT.bottom, bottom = twoG)
            }
        )
        ElevatedCard(
            onClick = {},
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.constrainAs(telegramB) {
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
                linkTo(start = parent.start, end = parent.end, startMargin = 16.dp, endMargin = 16.dp)
                linkTo(top = twoG, bottom = threeG, topMargin = 20.dp, bottomMargin = 10.dp)
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_telegram_round),
                    contentDescription = stringResource(id = R.string.cd_via_telegram),
                    modifier = Modifier.weight(0.2F)
                )
                Text(
                    text = stringResource(id = R.string.via_telegram),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.8F)
                )
            }
        }
        ElevatedCard(
            onClick = {},
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.constrainAs(mailB) {
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
                linkTo(start = parent.start, end = parent.end, startMargin = 16.dp, endMargin = 16.dp)
                linkTo(top = threeG, bottom = parent.bottom, topMargin = 10.dp, bottomMargin = 20.dp)
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_mail_round),
                    contentDescription = stringResource(id = R.string.cd_via_email),
                    modifier = Modifier.weight(0.2F)
                )
                Text(
                    text = stringResource(id = R.string.via_email),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.8F)
                )
            }
        }
    }
}