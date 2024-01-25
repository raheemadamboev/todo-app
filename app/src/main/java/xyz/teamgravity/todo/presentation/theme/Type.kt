package xyz.teamgravity.todo.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import xyz.teamgravity.todo.R

private val muli: FontFamily = FontFamily(
    Font(R.font.muli, FontWeight.Normal),
    Font(R.font.muli_medium, FontWeight.Medium),
    Font(R.font.muli_bold, FontWeight.Bold),
    Font(R.font.muli_black, FontWeight.Black)
)

private val default: Typography = Typography()

val Typography: Typography = Typography(
    displayLarge = default.displayLarge.copy(fontFamily = muli),
    displayMedium = default.displayMedium.copy(fontFamily = muli),
    displaySmall = default.displaySmall.copy(fontFamily = muli),
    headlineLarge = default.headlineLarge.copy(fontFamily = muli),
    headlineMedium = default.headlineMedium.copy(fontFamily = muli),
    headlineSmall = default.headlineSmall.copy(fontFamily = muli),
    titleLarge = default.titleLarge.copy(fontFamily = muli),
    titleMedium = default.titleMedium.copy(fontFamily = muli),
    titleSmall = default.titleSmall.copy(fontFamily = muli),
    labelLarge = default.labelLarge.copy(fontFamily = muli),
    labelMedium = default.labelMedium.copy(fontFamily = muli),
    labelSmall = default.labelSmall.copy(fontFamily = muli),
    bodyLarge = default.bodyLarge.copy(fontFamily = muli),
    bodyMedium = default.bodyMedium.copy(fontFamily = muli),
    bodySmall = default.bodySmall.copy(fontFamily = muli)
)