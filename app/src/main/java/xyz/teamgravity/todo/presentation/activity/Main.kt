package xyz.teamgravity.todo.presentation.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.core.util.Preferences
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class Main : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setLanguage() // TODO ???
        super.onCreate(savedInstanceState)
        setContent {

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