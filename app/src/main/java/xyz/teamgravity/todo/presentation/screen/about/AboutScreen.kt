package xyz.teamgravity.todo.presentation.screen.about

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.todo.presentation.component.misc.WindowInfo
import xyz.teamgravity.todo.presentation.component.misc.rememberWindowInfo

@Destination
@Composable
fun AboutScreen(
    navigator: DestinationsNavigator
) {
    when (rememberWindowInfo().screenWidthInfo) {
        WindowInfo.WindowType.Compact -> AboutPortraitScreen(onBackButtonClick = navigator::popBackStack)
        else -> AboutLandscapeScreen(onBackButtonClick = navigator::popBackStack)
    }
}