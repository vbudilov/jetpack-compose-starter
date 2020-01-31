package com.budilov.starter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.tooling.preview.Preview
import com.budilov.starter.model.AuthStatus
import com.budilov.starter.model.Navigation
import com.budilov.starter.ui.MyAppUI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppUI(
                authStatus = AuthStatus(false),
                navigation = Navigation()
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyAppUI(AuthStatus(true))
}