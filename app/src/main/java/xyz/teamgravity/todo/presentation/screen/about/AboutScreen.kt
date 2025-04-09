package xyz.teamgravity.todo.presentation.screen.about

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.todo.presentation.navigation.MainNavGraph

@Destination<MainNavGraph>
@Composable
fun AboutScreen(
    navigator: DestinationsNavigator
) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> AboutLandscapeScreen(onBackButtonClick = navigator::navigateUp)
        else -> AboutPortraitScreen(onBackButtonClick = navigator::navigateUp)
    }
}