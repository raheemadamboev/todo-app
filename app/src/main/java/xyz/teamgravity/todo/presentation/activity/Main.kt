package xyz.teamgravity.todo.presentation.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.core.util.Preferences
import xyz.teamgravity.todo.presentation.screen.NavGraphs
import xyz.teamgravity.todo.presentation.theme.SuperLightWhite
import xyz.teamgravity.todo.presentation.theme.TodoTheme
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class Main : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage() // TODO ???
        setContent {
            TodoTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }

    // set language for app
    @Suppress("DEPRECATION")
    private fun setLanguage() {
        lifecycleScope.launch {
            val language = preferences.preferences.first().language

            if (!language.equals("def", true)) {
                val locale = Locale(language)
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
            }
        }
    }
}