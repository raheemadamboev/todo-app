package xyz.teamgravity.todo.presentation.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val SuperLightWhite = Color(0xFFF2F2F2)
val Brown200 = Color(0xFFB07E5D)
val Brown500 = Color(0xFF7E5233)
val Brown700 = Color(0xFF4F290C)
val PlayStoreGray = Color(0xFF474747)
val DarkGray = Color(0xFF121212)
val DarkGray200 = Color(0xFF1A1A1A)
val White700 = Color(0xFFBBBBBB)

@get:Composable
val Colors.backgroundLayout: Color
    get() = if (isLight) SuperLightWhite else DarkGray

@get:Composable
val Colors.backgroundCard: Color
    get() = if (isLight) White else DarkGray200

@get:Composable
val Colors.textPrimary: Color
    get() = if (isLight) Black else White

@get:Composable
val Colors.textSecondary: Color
    get() = if (isLight) PlayStoreGray else White700

@get:Composable
val Colors.textButton: Color
    get() = if (isLight) White else Black