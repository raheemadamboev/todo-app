package xyz.teamgravity.todo.presentation.screen.support

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.core.util.Helper
import xyz.teamgravity.todo.presentation.component.card.CardConnection
import xyz.teamgravity.todo.presentation.component.topbar.TopBarIconButton
import xyz.teamgravity.todo.presentation.theme.White

@Composable
fun SupportPortraitScreen(
    onBackButtonClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Spacer(
                    modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    TopBarIconButton(
                        onClick = onBackButtonClick,
                        icon = Icons.AutoMirrored.Filled.ArrowBack,
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
                    Spacer(
                        modifier = Modifier.width(38.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.sticker_customer_service),
                    contentDescription = stringResource(id = R.string.cd_user_support),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2F)
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                Text(
                    text = stringResource(id = R.string.connect_us),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(id = R.string.connect_us_body),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            CardConnection(
                onClick = {
                    Helper.connectViaTelegram(context)
                },
                icon = R.drawable.ic_telegram,
                title = R.string.via_telegram,
                contentDescription = R.string.cd_via_telegram,
                fillMaxSize = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            CardConnection(
                onClick = {
                    Helper.connectViaEmail(context)
                },
                icon = R.drawable.ic_email,
                title = R.string.via_email,
                contentDescription = R.string.cd_via_email,
                fillMaxSize = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }
}