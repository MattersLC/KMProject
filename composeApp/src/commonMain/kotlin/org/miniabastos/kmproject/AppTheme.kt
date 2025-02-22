package org.miniabastos.kmproject

import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            small = AbsoluteCutCornerShape(0.dp),
            medium = AbsoluteCutCornerShape(0.dp),
            large = AbsoluteCutCornerShape(0.dp)
        )
    ) {
        content()
    }
}

@Composable
fun getColorsTheme(): DarkModeColors {
    val isDarkMode = false;

    val Purple = Color(0xFF6A66FF)
    val ExpenseItem = if (isDarkMode) Color(0xFF090808) else Color(0xFFF1F1F1)
    val Background = if (isDarkMode) Color(0xFF1E1C1C) else Color.White
    val TextColor = if (isDarkMode) Color.White else Color.Black
    val AddIconColor = if (isDarkMode) Purple else Color.Black
    val ArrowRound = if (isDarkMode) Purple else Color.Gray.copy(alpha = .2f)

    return DarkModeColors(
        purple = Purple,
        expenseItem = ExpenseItem,
        background = Background,
        textColor = TextColor,
        addIconColor = AddIconColor,
        arrowRound = ArrowRound,
    )
}

data class DarkModeColors(
    val purple: Color,
    val expenseItem: Color,
    val background: Color,
    val textColor: Color,
    val addIconColor: Color,
    val arrowRound: Color,
)