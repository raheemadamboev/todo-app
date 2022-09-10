package xyz.teamgravity.todo.presentation.screen.about

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.todo.presentation.navigation.MainNavGraph

@MainNavGraph
@Destination
@Composable
fun AboutScreen(
    navigator: DestinationsNavigator
) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> AboutPortraitScreen(onBackButtonClick = navigator::popBackStack)
        else -> AboutLandscapeScreen(onBackButtonClick = navigator::popBackStack)
    }
}