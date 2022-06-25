package xyz.teamgravity.todo.presentation.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import xyz.teamgravity.todo.BuildConfig
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.core.util.Helper
import xyz.teamgravity.todo.presentation.component.text.TextPlain
import xyz.teamgravity.todo.presentation.component.topbar.TopBar
import xyz.teamgravity.todo.presentation.component.topbar.TopBarIconButton

@Composable
fun AboutLandscapeScreen(
    onBackButtonClick: () -> Unit
) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                title = { TextPlain(id = R.string.app_name) },
                navigationIcon = {
                    TopBarIconButton(
                        onClick = onBackButtonClick,
                        icon = Icons.Default.ArrowBack,
                        contentDescription = R.string.cd_back_button
                    )
                }
            )
        }
    ) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val (appI, appNameT, appVersionT, oneS, companyC) = createRefs()
            val oneG = createGuidelineFromStart(0.5F)

            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = stringResource(id = R.string.cd_app_icon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(appI) {
                        width = Dimension.value(180.dp)
                        height = Dimension.value(180.dp)
                        linkTo(start = parent.start, end = oneG)
                        linkTo(top = parent.top, bottom = parent.bottom)
                    }
            )
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .constrainAs(appNameT) {
                        width = Dimension.fillToConstraints
                        linkTo(start = oneG, end = parent.end)
                    }
            )
            Text(
                text = BuildConfig.VERSION_NAME,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(appVersionT) {
                        width = Dimension.fillToConstraints
                        linkTo(start = oneG, end = parent.end)
                    }
            )
            Spacer(
                modifier = Modifier
                    .constrainAs(oneS) {
                        height = Dimension.value(16.dp)
                        linkTo(start = oneG, end = parent.end)
                    }
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .clickable { Helper.viewGravityPage(context) }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .constrainAs(companyC) {
                        linkTo(start = oneG, end = parent.end)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gravity),
                    contentDescription = stringResource(id = R.string.cd_company_logo),
                    modifier = Modifier
                        .width(100.dp)
                        .height(20.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.raheem),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                )
            }

            createVerticalChain(appNameT, appVersionT, oneS, companyC, chainStyle = ChainStyle.Packed)
        }
    }
}