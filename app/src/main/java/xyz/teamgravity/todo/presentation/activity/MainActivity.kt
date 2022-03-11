package xyz.teamgravity.todo.presentation.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.databinding.ActivityMainBinding
import xyz.teamgravity.todo.helper.util.Preferences
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setLanguage()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // find nav controller in activity
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        // set up action bar back button
        setupActionBarWithNavController(navController)
    }

    // set language for app
    @Suppress("DEPRECATION")
    private fun setLanguage() {
        lifecycleScope.launchWhenCreated {
            val language = preferences.preferencesFlow.first().language

            if (!language.equals("def", true)) {
                val locale = Locale(language)
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

                // update title
                this@MainActivity.supportActionBar?.title = getString(R.string.tasks)
            }
        }
    }

    // back button in action bar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}