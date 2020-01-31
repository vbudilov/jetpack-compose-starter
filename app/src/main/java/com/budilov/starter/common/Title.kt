package com.budilov.starter.common

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.graphics.Color
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontFamily
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.sp

@Composable
fun Title(text: String, color: Color) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily("Roboto"),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = color
        )
    )
}