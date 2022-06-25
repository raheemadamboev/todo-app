package xyz.teamgravity.todo.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = BrownGrey30,
    onPrimary = White,
    primaryContainer = Brown30,
    onPrimaryContainer = White700,
    inversePrimary = Brown40,
    secondary = DarkBrown80,
    onSecondary = DarkBrown20,
    secondaryContainer = DarkBrown30,
    onSecondaryContainer = DarkBrown90,
    tertiary = Violet80,
    onTertiary = Violet20,
    tertiaryContainer = Violet30,
    onTertiaryContainer = Violet90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = DarkGray,
    onBackground = White700,
    surface = DarkGray200,
    onSurface = White700,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = DarkGray200,
    onSurfaceVariant = White700,
    outline = BrownGrey80
)

private val LightColorScheme = lightColorScheme(
    primary = Brown40,
    onPrimary = White,
    primaryContainer = Brown90,
    onPrimaryContainer = Brown10,
    inversePrimary = Brown80,
    secondary = DarkBrown40,
    onSecondary = White,
    secondaryContainer = DarkBrown90,
    onSecondaryContainer = DarkBrown10,
    tertiary = Violet40,
    onTertiary = White,
    tertiaryContainer = Violet90,
    onTertiaryContainer = Violet10,
    error = Red40,
    onError = White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = BrownGrey90,
    onSurface = BrownGrey30,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = BrownGrey90,
    onSurfaceVariant = BrownGrey30,
    outline = BrownGrey50
)

@Composable
fun TodoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}