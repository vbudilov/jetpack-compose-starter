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
            fontSize = 20.sp,
            color = color
        )
    )
}

@Composable
fun InputError(text: String, color: Color = Color.Red) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily("Roboto"),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = color
        )
    )
}