package xyz.teamgravity.todo.presentation.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import xyz.teamgravity.todo.BuildConfig
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.component.text.TextPlain
import xyz.teamgravity.todo.presentation.component.topbar.TopBarIconButton
import xyz.teamgravity.todo.presentation.theme.Muli

@Composable
fun AboutPortraitScreen(
    onBackButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
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
            val (appI, appNameT, appVersionT, companyI, developerT) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = stringResource(id = R.string.cd_app_icon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(appI) {
                        width = Dimension.value(180.dp)
                        height = Dimension.value(180.dp)
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(top = parent.top, bottom = parent.bottom, bias = 0.4F)
                    }
            )
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Black,
                fontFamily = Muli,
                modifier = Modifier
                    .constrainAs(appNameT) {
                        width = Dimension.matchParent
                        top.linkTo(appI.bottom, 20.dp)
                    }
            )
            Text(
                text = BuildConfig.VERSION_NAME,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(appVersionT) {
                        width = Dimension.matchParent
                        top.linkTo(appNameT.bottom, 10.dp)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.gravity),
                contentDescription = stringResource(id = R.string.cd_company_logo),
                modifier = Modifier
                    .constrainAs(companyI) {
                        width = Dimension.value(100.dp)
                        height = Dimension.value(20.dp)
                        linkTo(start = parent.start, end = parent.end)
                        bottom.linkTo(developerT.top, 5.dp)
                    }
            )
            Text(
                text = stringResource(id = R.string.raheem),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(developerT) {
                        width = Dimension.matchParent
                        bottom.linkTo(parent.bottom, 16.dp)
                    }
            )
        }
    }
}