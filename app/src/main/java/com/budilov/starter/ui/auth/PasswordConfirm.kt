package com.budilov.starter.ui.auth

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.ButtonStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.budilov.starter.common.InputError
import com.budilov.starter.common.TextBox
import com.budilov.starter.common.Title
import com.budilov.starter.service.auth.CognitoAuthService
import com.budilov.starter.service.auth.LoginStateEnum
import com.budilov.starter.ui.AvailableTopLevelScreens
import com.budilov.starter.ui.colors
import com.budilov.starter.ui.topLevelNavigation

data class PasswordConfirmInput(
    var username: String = "",
    var password: String = ""
)

enum class PasswordConfirmStateEnum {
    SMS_MFA, LOGGED_IN, NEW_PASSWORD_REQUIRED, LOGIN_ERROR
}

data class PasswordConfirmState(var state: LoginStateEnum? = null, var message: String? = null)

@Composable
fun PasswordConfirm(username: String) {

    val loginInput = LoginInput()
    var loginOutput = state { "" }


    VerticalScroller {

        Column(arrangement = Arrangement.SpaceEvenly) {
            Spacer(modifier = LayoutHeight(100.dp))
            Title(
                text = "Confirm your account",
                color = colors.primary
            )

            if (loginOutput.value.isNotBlank())
                InputError(text = loginOutput.value)

            Spacer(modifier = LayoutHeight(30.dp))
            Text(text = username)

            Spacer(modifier = LayoutHeight(5.dp))
            TextBox(
                hint = "confirmation code",
                onValueChange = { input -> loginInput.password = input })

            Spacer(modifier = LayoutHeight(15.dp))
            Container(alignment = Alignment.Center, expanded = true) {

                Button(modifier = LayoutWidth.Fill, text = "Login", style = ButtonStyle(
                    elevation = 3.dp,
                    backgroundColor = colors.primary, contentColor = Color.White,
                    shape = RoundedCornerShape(3.dp)
                ),
                    onClick = {

                        CognitoAuthService.signIn(
                            loginInput.username,
                            loginInput.password
                        ) {
                            // Have to do a try..catch since otherwise I'm getting a java.lang.IllegalStateException: Not in a frame
                            // Not sure why so it's a workaround for now

                            when (it.state) {
                                LoginStateEnum.LOGGED_IN -> {

                                    loginOutput.value = it.message.toString()
                                    topLevelNavigation(
                                        AvailableTopLevelScreens.HOME

                                    )
                                }
                                LoginStateEnum.LOGIN_ERROR -> {
                                    loginOutput.value = it.message.toString()
                                }
                            }
                        }
                    })
            }
        }
    }
}

@Preview
@Composable
fun PasswordConfirmScreenPreview() {
    PasswordConfirm("hello@there.com")
}