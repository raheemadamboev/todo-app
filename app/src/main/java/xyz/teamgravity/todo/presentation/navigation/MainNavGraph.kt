package xyz.teamgravity.todo.presentation.navigation

import com.ramcosta.composedestinations.animations.defaults.DefaultFadingTransitions
import com.ramcosta.composedestinations.annotation.NavHostGraph

@NavHostGraph(
    defaultTransitions = DefaultFadingTransitions::class
)
annotation class MainNavGraph(
    val start: Boolean = false
)
