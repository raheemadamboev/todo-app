package xyz.teamgravity.todo.presentation.navigation

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import xyz.teamgravity.todo.presentation.screen.NavGraphs

@Composable
fun Navigation() {
    DestinationsNavHost(navGraph = NavGraphs.main)
}