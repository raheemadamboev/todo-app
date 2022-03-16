package xyz.teamgravity.todo.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import xyz.teamgravity.todo.presentation.navigation.Navigation
import xyz.teamgravity.todo.presentation.theme.TodoTheme

@AndroidEntryPoint
class Main : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                Navigation()
            }
        }
    }
}