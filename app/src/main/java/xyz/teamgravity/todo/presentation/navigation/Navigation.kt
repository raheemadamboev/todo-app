package xyz.teamgravity.todo.presentation.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.TodoAddScreenDestination
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.ramcosta.composedestinations.spec.NavHostEngine
import xyz.teamgravity.todo.core.constant.Shortcuts
import xyz.teamgravity.todo.presentation.activity.Main

@Composable
fun Navigation(
    intent: Intent?,
    engine: NavHostEngine = rememberNavHostEngine(),
    controller: NavHostController = engine.rememberNavController()
) {
    LaunchedEffect(
        key1 = intent,
        block = {
            Shortcuts.fromId(intent?.getStringExtra(Main.EXTRA_SHORTCUT_ID))?.let { shortcut ->
                when (shortcut) {
                    Shortcuts.AddTodo -> {
                        controller.navigate(TodoAddScreenDestination.route)
                    }
                }
            }
        }
    )

    DestinationsNavHost(
        navGraph = NavGraphs.main,
        engine = engine,
        navController = controller
    )
}