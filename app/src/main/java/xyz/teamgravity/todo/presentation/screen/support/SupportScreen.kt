package xyz.teamgravity.todo.presentation.screen.support

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.todo.presentation.component.misc.WindowInfo
import xyz.teamgravity.todo.presentation.component.misc.rememberWindowInfo

@Destination
@Composable
fun SupportScreen(
    navigator: DestinationsNavigator
) {
    when (rememberWindowInfo().screenWidthInfo) {
        WindowInfo.WindowType.Compact -> SupportPortraitScreen(onBackButtonClick = navigator::popBackStack)
        else -> SupportLandscapeScreen(onBackButtonClick = navigator::popBackStack)
    }
}