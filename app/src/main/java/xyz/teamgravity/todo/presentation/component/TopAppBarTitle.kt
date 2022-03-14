package xyz.teamgravity.todo.presentation.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import xyz.teamgravity.todo.R

@Composable
fun TopAppBarTitle(
    title: String
) {
    Text(
        text = title,
        fontFamily = FontFamily(listOf(Font(R.font.muli_black)))
    )
}