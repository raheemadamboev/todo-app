package xyz.teamgravity.todo.presentation.screen.about

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.todo.presentation.component.misc.WindowInfo
import xyz.teamgravity.todo.presentation.component.misc.rememberWindowInfo

@Destination
@Composable
fun AboutScreen(
    scaffold: ScaffoldState = rememberScaffoldState(),
    navigator: DestinationsNavigator
) {
    when (rememberWindowInfo().screenWidthInfo) {
        WindowInfo.WindowType.Compact -> AboutPortraitScreen(
            scaffold = scaffold,
            onBackButtonClick = navigator::popBackStack
        )
        else -> AboutLandscapeScreen(
            scaffold = scaffold,
            onBackButtonClick = navigator::popBackStack
        )
    }
}