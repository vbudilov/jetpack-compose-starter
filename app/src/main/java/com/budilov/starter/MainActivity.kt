package com.budilov.starter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.tooling.preview.Preview
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserStateDetails
import com.budilov.starter.ui.MyAppUI

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        AWSMobileClient.getInstance()
            .initialize(applicationContext, object : Callback<UserStateDetails?> {

                override fun onError(e: Exception?) {
                    Log.e(TAG, "Initialization error.", e)
                }

                override fun onResult(result: UserStateDetails?) {
                    Log.i(TAG, "onResult: " + result?.userState)

                }
            }
            )

        setContent {
            MyAppUI()
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyAppUI()
}