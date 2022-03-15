package xyz.teamgravity.todo.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.todo.BuildConfig
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.component.topbar.TopBarTitle

@Destination
@Composable
fun AboutScreen(
    scaffold: ScaffoldState = rememberScaffoldState(),
    navigator: DestinationsNavigator
) {
    Scaffold(
        scaffoldState = scaffold,
        topBar = {
            TopAppBar(
                title = { TopBarTitle(title = R.string.app_name) },
                navigationIcon = {
                    IconButton(onClick = navigator::popBackStack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.cd_back_button)
                        )
                    }
                }
            )
        }
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (appI, appNameT, appVersionT, companyI, developerT) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = stringResource(id = R.string.cd_app_icon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(180.dp)
                    .height(180.dp)
                    .constrainAs(appI) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(top = parent.top, bottom = parent.bottom, bias = 0.4F)
                    }
            )
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(listOf(Font(R.font.muli_black))),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(appNameT) {
                        top.linkTo(appI.bottom, 20.dp)
                    }
            )
            Text(
                text = BuildConfig.VERSION_NAME,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(appVersionT) {
                        top.linkTo(appNameT.bottom, 10.dp)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.gravity),
                contentDescription = stringResource(id = R.string.cd_company_logo),
                modifier = Modifier
                    .width(100.dp)
                    .height(20.dp)
                    .constrainAs(companyI) {
                        linkTo(start = parent.start, end = parent.end)
                        bottom.linkTo(developerT.top, 5.dp)
                    }
            )
            Text(
                text = stringResource(id = R.string.raheem),
                style = MaterialTheme.typography.body1,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(developerT) {
                        bottom.linkTo(parent.bottom, 16.dp)
                    }
            )
        }
    }
}