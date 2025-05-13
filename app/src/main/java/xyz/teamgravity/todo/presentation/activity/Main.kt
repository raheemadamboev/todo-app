package xyz.teamgravity.todo.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import xyz.teamgravity.todo.presentation.navigation.Navigation
import xyz.teamgravity.todo.presentation.theme.TodoTheme

@AndroidEntryPoint
class Main : ComponentActivity() {

    companion object {
        const val EXTRA_SHORTCUT_ID = "Main_extraShortcutId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val intent = if (savedInstanceState == null) intent else null
        setContent {
            TodoTheme {
                Navigation(intent)
            }
        }
    }
}