package xyz.teamgravity.todo.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import xyz.teamgravity.todo.R

val Muli = FontFamily(
    Font(R.font.muli, FontWeight.Normal),
    Font(R.font.muli_black, FontWeight.Black)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Muli,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = Muli,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    )
)