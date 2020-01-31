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
import com.budilov.starter.ui.auth.LoginInput

enum class InputHintLocation {
    TOP, INLINE
}

@Composable
fun TextBox(
    loginInput: LoginInput,
    hint: String = "username",
    hintLocation: InputHintLocation = InputHintLocation.TOP,
    color: Color = Color.LightGray,
    height: Dp = 50.dp
) {

    val state = state { "" }
    if (hintLocation == InputHintLocation.INLINE)
        state.value = if (loginInput.usernameEntered) loginInput.username else hint

    var hideHint = false

    // Let's round out the corners
    Surface(color = color, shape = RoundedCornerShape(14.dp)) {
        Container(height = height) {
            Padding(left = 6.dp) {
                Container(alignment = Alignment.CenterLeft) {
                    Column() {
                        if (hintLocation == InputHintLocation.TOP && !hideHint)
                            Text(text = hint)
                        Padding(left = 10.dp, top = 10.dp) {

                            TextField(
                                value = state.value,
                                keyboardType = KeyboardType.Text,
                                onFocus = {
                                    if (!loginInput.usernameEntered)
                                        state.value = ""
                                    hideHint = true
                                },
                                onBlur = {
                                    hideHint = false
                                },
                                onValueChange = {
                                    loginInput.usernameEntered = true
                                    loginInput.username = it
                                    state.value = loginInput.username
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
    loginInput: LoginInput,
    hint: String = "password",
    hintLocation: InputHintLocation = InputHintLocation.TOP,
    color: Color = Color.LightGray,
    height: Dp = 50.dp
) {
    val state = state { "" }
    if (hintLocation == InputHintLocation.INLINE)
        state.value = if (loginInput.usernameEntered) loginInput.username else hint

    var hideHint = false

    Surface(color = color, shape = RoundedCornerShape(14.dp)) {
        Container(height = height) {
            Padding(padding = 6.dp) {
                Container(alignment = Alignment.CenterLeft) {
                    Column() {
                        if (hintLocation == InputHintLocation.TOP)
                            Text(hint)
                        Padding(left = 10.dp, top = 10.dp) {

                            PasswordTextField(
                                value = state.value,
                                onFocus = {
                                    if (!loginInput.usernameEntered)
                                        state.value = ""
                                    hideHint = !hideHint
                                },
                                onBlur = {
                                    hideHint = !hideHint
                                },
                                onValueChange = {
                                    loginInput.usernameEntered = true
                                    loginInput.username = it
                                    state.value = loginInput.username
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}