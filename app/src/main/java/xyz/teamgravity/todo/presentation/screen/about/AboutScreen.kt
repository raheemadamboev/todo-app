package xyz.teamgravity.todo.presentation.screen.about

import android.content.res.Configuration
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val onBackButtonClick: () -> Unit = remember { { dispatcher?.onBackPressed() ?: navigator.navigateUp() } }

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> AboutLandscapeScreen(onBackButtonClick = onBackButtonClick)
        else -> AboutPortraitScreen(onBackButtonClick = onBackButtonClick)
    }
}