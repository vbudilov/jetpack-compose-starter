package com.budilov.starter.service.auth

import android.util.Log
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserState
import com.amazonaws.mobile.client.results.*

/**
 *
 * https://aws-amplify.github.io/docs/android/authentication
 *
 */
object CognitoAuthService {

    fun loggedIn() {
        AWSMobileClient.getInstance().addUserStateListener { userStateDetails ->
            when (userStateDetails.userState) {
                UserState.GUEST -> Log.i("userState", "user is in guest mode")
                UserState.SIGNED_OUT -> Log.i("userState", "user is signed out")
                UserState.SIGNED_IN -> Log.i("userState", "user is signed in")
                UserState.SIGNED_OUT_USER_POOLS_TOKENS_INVALID -> Log.i(
                    "userState",
                    "need to login again"
                )
                UserState.SIGNED_OUT_FEDERATED_TOKENS_INVALID -> Log.i(
                    "userState",
                    "user logged in via federation, but currently needs new tokens"
                )
                else -> Log.e("userState", "unsupported")
            }
        }
    }

    fun signUp(username: String, password: String) {

        val attributes: MutableMap<String, String> = HashMap()
        attributes["email"] = "name@email.com"
        AWSMobileClient.getInstance()
            .signUp(username, password, attributes, null, object : Callback<SignUpResult?> {
                override fun onResult(signUpResult: SignUpResult?) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.d(
                            null,
                            "Sign-up callback state: " + signUpResult?.confirmationState
                        )
                        if (signUpResult?.confirmationState != true) {
                            val details: UserCodeDeliveryDetails? =
                                signUpResult?.userCodeDeliveryDetails
                            Log.i(null, "Confirm sign-up with: " + details?.destination)
                        } else {
                            Log.i(null, "Sign-up done.")
                        }
                    })
                }

                override fun onError(e: Exception?) {
                    Log.e(null, "Sign-up error", e)
                }

            })
    }

    fun confirmSignup(username: String, code: String) {
        AWSMobileClient.getInstance().confirmSignUp(
            username,
            code,
            object :
                Callback<SignUpResult> {
                override fun onResult(signUpResult: SignUpResult) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.d(
                            null,
                            "Sign-up callback state: " + signUpResult.confirmationState
                        )
                        if (!signUpResult.confirmationState) {
                            val details =
                                signUpResult.userCodeDeliveryDetails
                            Log.d(null, "Confirm sign-up with: " + details.destination)
                        } else {
                            Log.d(null, "Sign-up done.")
                        }
                    })
                }

                override fun onError(e: java.lang.Exception) {
                    Log.e(null, "Confirm sign-up error", e)
                }
            })
    }

    fun resendConfirmationCode(username: String) {
        AWSMobileClient.getInstance().resendSignUp(
            username,
            object :
                Callback<SignUpResult> {
                override fun onResult(signUpResult: SignUpResult) {
                    Log.i(
                        null, "A verification code has been sent via" +
                                signUpResult.userCodeDeliveryDetails.deliveryMedium
                                + " at " +
                                signUpResult.userCodeDeliveryDetails.destination
                    )
                }

                override fun onError(e: java.lang.Exception) {
                    Log.e(null, e.toString())
                }
            })
    }

    fun signIn(username: String, password: String) {
        AWSMobileClient.getInstance().signIn(
            username,
            password,
            null,
            object : Callback<SignInResult> {
                override fun onResult(signInResult: SignInResult) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.d(
                            null,
                            "Sign-in callback state: " + signInResult.signInState
                        )
                        when (signInResult.signInState) {
                            SignInState.DONE -> {
                                println("Sign-in done.")
                            }
                            SignInState.SMS_MFA -> println("Please confirm sign-in with SMS.")
                            SignInState.NEW_PASSWORD_REQUIRED -> println("Please confirm sign-in with new password.")
                            else -> println("Unsupported sign-in confirmation: " + signInResult.signInState)
                        }
                    })
                }

                override fun onError(e: java.lang.Exception) {
                    Log.e(null, "Sign-in error", e)
                }
            })
    }

    fun confirmSignIn(password: String) {
        AWSMobileClient.getInstance().confirmSignIn(
            password,
            object : Callback<SignInResult> {
                override fun onResult(signInResult: SignInResult) {
                    Log.d(
                        null,
                        "Sign-in callback state: " + signInResult.signInState
                    )
                    when (signInResult.signInState) {
                        SignInState.DONE -> println("Sign-in done.")
                        SignInState.SMS_MFA -> println("Please confirm sign-in with SMS.")
                        else -> println("Unsupported sign-in confirmation: " + signInResult.signInState)
                    }
                }

                override fun onError(e: java.lang.Exception) {
                    Log.e(null, "Sign-in error", e)
                }
            })
    }

    fun forgotPassword(username: String) {
        AWSMobileClient.getInstance().forgotPassword(
            "username",
            object : Callback<ForgotPasswordResult> {
                override fun onResult(result: ForgotPasswordResult) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.d(null, "forgot password state: " + result.state)
                        when (result.state) {
                            ForgotPasswordState.CONFIRMATION_CODE -> println("Confirmation code is sent to reset password")
                            else -> Log.e(
                                null,
                                "un-supported forgot password state"
                            )
                        }
                    })
                }

                override fun onError(e: java.lang.Exception) {
                    Log.e(null, "forgot password error", e)
                }
            })
    }

    fun confirmForgotPassword(password: String, code: String) {
        AWSMobileClient.getInstance().confirmForgotPassword(
            password,
            code,
            object : Callback<ForgotPasswordResult> {
                override fun onResult(result: ForgotPasswordResult) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.d(null, "forgot password state: " + result.state)
                        when (result.state) {
                            ForgotPasswordState.DONE -> println("Password changed successfully")
                            else -> Log.e(
                                null,
                                "un-supported forgot password state"
                            )
                        }
                    })
                }

                override fun onError(e: java.lang.Exception) {
                    Log.e(null, "forgot password error", e)
                }
            })
    }
}