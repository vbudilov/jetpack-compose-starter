package com.budilov.starter.common

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.PasswordTextField
import androidx.ui.core.Text
import androidx.ui.core.TextField
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.input.KeyboardType
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.Padding
import androidx.ui.material.surface.Surface
import androidx.ui.unit.Dp
import androidx.ui.unit.dp

enum class InputHintLocation {
    TOP, INLINE
}

@Composable
fun TextBox(
    hint: String = "username",
    hintLocation: InputHintLocation = InputHintLocation.TOP,
    color: Color = Color.LightGray,
    height: Dp = 60.dp,
    onValueChange: (input: String) -> Unit
) {

    val input = if (InputHintLocation.INLINE == hintLocation) state { hint } else state { "" }

    val inputEntered = state { false }

    // Let's round out the corners
    Surface(color = color, shape = RoundedCornerShape(14.dp)) {
        Container(height = height) {
            Padding(left = 6.dp) {
                Container(alignment = Alignment.CenterLeft) {
                    Column {
                        if (hintLocation == InputHintLocation.TOP)
                            Text(text = hint)
                        Padding(left = 6.dp, top = 6.dp, bottom = 10.dp) {

                            TextField(
                                value = input.value,
                                keyboardType = KeyboardType.Text,
                                onFocus = {
                                    if (hintLocation == InputHintLocation.INLINE)
                                        if (inputEntered.value)
                                            input.value = ""
                                },
                                onValueChange = {
                                    if (it.isNotBlank())
                                        inputEntered.value = true
                                    input.value = it
                                    onValueChange(it)
                                }
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun PasswordBox(
    hint: String = "password",
    hintLocation: InputHintLocation = InputHintLocation.TOP,
    color: Color = Color.LightGray,
    height: Dp = 60.dp,
    onValueChange: (input: String) -> Unit
) {

    val input = if (InputHintLocation.INLINE == hintLocation) state { hint } else state { "" }

    val inputEntered = state { false }

    Surface(color = color, shape = RoundedCornerShape(14.dp)) {
        Container(height = height) {
            Padding(padding = 6.dp) {
                Container(alignment = Alignment.CenterLeft) {
                    Column {
                        if (hintLocation == InputHintLocation.TOP)
                            Text(hint)
                        Padding(left = 6.dp, top = 6.dp, bottom = 10.dp) {

                            PasswordTextField(
                                value = input.value,
                                onFocus = {
                                    if (!inputEntered.value)
                                        input.value = ""
                                },
                                onValueChange = {
                                    if (it.isNotBlank())
                                        inputEntered.value = true

                                    input.value = it
                                    onValueChange(it)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}