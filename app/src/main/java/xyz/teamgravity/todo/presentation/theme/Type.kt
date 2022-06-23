package xyz.teamgravity.todo.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import xyz.teamgravity.todo.R

val Muli = FontFamily(
    Font(R.font.muli, FontWeight.Normal),
    Font(R.font.muli_medium, FontWeight.Medium),
    Font(R.font.muli_bold, FontWeight.Bold),
    Font(R.font.muli_black, FontWeight.Black)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Muli,
        lineHeight = 64.sp,
        fontSize = 57.sp,
        letterSpacing = (-0.2).sp,
        fontWeight = FontWeight.W400
    ),
    displayMedium = TextStyle(
        fontFamily = Muli,
        lineHeight = 52.sp,
        fontSize = 45.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.W400
    ),
    displaySmall = TextStyle(
        fontFamily = Muli,
        lineHeight = 44.sp,
        fontSize = 36.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.W400
    ),
    headlineLarge = TextStyle(
        fontFamily = Muli,
        lineHeight = 40.sp,
        fontSize = 32.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.W400
    ),
    headlineMedium = TextStyle(
        fontFamily = Muli,
        lineHeight = 36.sp,
        fontSize = 28.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.W400
    ),
    headlineSmall = TextStyle(
        fontFamily = Muli,
        lineHeight = 32.sp,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.W400
    ),
    titleLarge = TextStyle(
        fontFamily = Muli,
        lineHeight = 28.sp,
        fontSize = 22.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.W400
    ),
    titleMedium = TextStyle(
        fontFamily = Muli,
        lineHeight = 24.sp,
        fontSize = 16.sp,
        letterSpacing = 0.2.sp,
        fontWeight = FontWeight.W500
    ),
    titleSmall = TextStyle(
        fontFamily = Muli,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.W500
    ),
    labelLarge = TextStyle(
        fontFamily = Muli,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.W500
    ),
    labelMedium = TextStyle(
        fontFamily = Muli,
        lineHeight = 16.sp,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.W500
    ),
    labelSmall = TextStyle(
        fontFamily = Muli,
        lineHeight = 16.sp,
        fontSize = 11.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.W500
    ),
    bodyLarge = TextStyle(
        fontFamily = Muli,
        lineHeight = 24.sp,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.W400
    ),
    bodyMedium = TextStyle(
        fontFamily = Muli,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        letterSpacing = 0.2.sp,
        fontWeight = FontWeight.W400
    ),
    bodySmall = TextStyle(
        fontFamily = Muli,
        lineHeight = 16.sp,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        fontWeight = FontWeight.W400
    )
)