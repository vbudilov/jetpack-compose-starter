package com.budilov.starter.service.auth

import android.util.Log
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.SignOutOptions
import com.amazonaws.mobile.client.UserState
import com.amazonaws.mobile.client.results.*
import com.budilov.starter.ui.auth.LoginState

private const val TAG = "CognitoAuthService"


/**
 *
 * https://aws-amplify.github.io/docs/android/authentication
 *
 */
object CognitoAuthService {

    fun loggedIn(callback: (com.budilov.starter.service.auth.UserState) -> Unit) {
        AWSMobileClient.getInstance().addUserStateListener { userStateDetails ->
            when (userStateDetails.userState) {
                UserState.GUEST -> {
                    Log.i("userState", "user is GUEST")
                    callback(com.budilov.starter.service.auth.UserState.GUEST)
                }
                UserState.SIGNED_OUT -> {
                    Log.i("userState", "user is signed out")
                    callback(com.budilov.starter.service.auth.UserState.SIGNED_OUT)
                }
                UserState.SIGNED_IN -> {
                    Log.i("userState", "user is signed in")
                    callback(com.budilov.starter.service.auth.UserState.SIGNED_IN)
                }
                UserState.SIGNED_OUT_USER_POOLS_TOKENS_INVALID -> {
                    Log.i(
                        "userState",
                        "need to login again"
                    )
                    callback(com.budilov.starter.service.auth.UserState.SIGNED_OUT_USER_POOLS_TOKENS_INVALID)
                }
                UserState.SIGNED_OUT_FEDERATED_TOKENS_INVALID -> {
                    Log.i(
                        "userState",
                        "user logged in via federation, but currently needs new tokens"
                    )
                    callback(com.budilov.starter.service.auth.UserState.SIGNED_OUT_FEDERATED_TOKENS_INVALID)
                }
                else -> {
                    Log.e("userState", "unsupported")
                    callback(com.budilov.starter.service.auth.UserState.UNKNOWN)
                }
            }
        }
    }

    /**
     * The classical sign-up flow where a user enters his username and password, as opposed to the password-less
     * flow where the user enters an email or phone number and the login code is emailed/sms'ed to him:
     * https://aws.amazon.com/blogs/mobile/implementing-passwordless-email-authentication-with-amazon-cognito/
     * :
     *
     */
    fun signUp(username: String, password: String, onSignUp: () -> Unit) {

        AWSMobileClient.getInstance()
            .signUp(username, password, null, null, object : Callback<SignUpResult?> {
                override fun onResult(signUpResult: SignUpResult?) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.d(
                            null,
                            "Sign-up callback state: " + signUpResult?.confirmationState
                        )
                        if (signUpResult?.confirmationState != true) {
                            val details: UserCodeDeliveryDetails? =
                                signUpResult?.userCodeDeliveryDetails
                            Log.i(TAG, "Confirm sign-up with: " + details?.destination)
                        } else {
                            Log.i(TAG, "Sign-up done.")
                        }
                    })
                }

                override fun onError(e: Exception?) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.e(TAG, "Sign-up error", e)
                    })
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
                            Log.d(TAG, "Confirm sign-up with: " + details.destination)
                        } else {
                            Log.d(TAG, "Sign-up done.")
                        }
                    })
                }

                override fun onError(e: java.lang.Exception) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.e(TAG, "Confirm sign-up error", e)
                    })
                }
            })
    }

    fun resendConfirmationCode(username: String) {
        AWSMobileClient.getInstance().resendSignUp(
            username,
            object :
                Callback<SignUpResult> {
                override fun onResult(signUpResult: SignUpResult) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.i(
                            null, "A verification code has been sent via" +
                                    signUpResult.userCodeDeliveryDetails.deliveryMedium
                                    + " at " +
                                    signUpResult.userCodeDeliveryDetails.destination
                        )
                    })
                }

                override fun onError(e: java.lang.Exception) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.e(TAG, e.toString())
                    })
                }
            })
    }

    fun signIn(
        username: String,
        password: String,
        onStateChange: (loginState: LoginState) -> Unit
    ) {
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
                                onStateChange(LoginState(LoginStateEnum.LOGGED_IN, "Success"))
                            }
                            SignInState.SMS_MFA -> {
                                println("Please confirm sign-in with SMS.")
                                onStateChange(
                                    LoginState(
                                        LoginStateEnum.SMS_MFA,
                                        "Please confirm sign-in with SMS"
                                    )
                                )
                            }
                            SignInState.NEW_PASSWORD_REQUIRED -> {
                                println("Please confirm sign-in with new password.")
                                onStateChange(
                                    LoginState(
                                        LoginStateEnum.NEW_PASSWORD_REQUIRED,
                                        "Please confirm sign-in with new password"
                                    )
                                )
                            }
                            else -> println("Unsupported sign-in confirmation: " + signInResult.signInState)
                        }
                    })
                }

                override fun onError(e: java.lang.Exception) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.e(TAG, "Couldn't sign in ${e}")
                        onStateChange(LoginState(LoginStateEnum.LOGIN_ERROR, e.message))
                    })
                }
            })
    }

    fun confirmSignIn(password: String) {
        AWSMobileClient.getInstance().confirmSignIn(
            password,
            object : Callback<SignInResult> {
                override fun onResult(signInResult: SignInResult) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.d(
                            null,
                            "Sign-in callback state: " + signInResult.signInState
                        )
                        when (signInResult.signInState) {
                            SignInState.DONE -> println("Sign-in done.")
                            SignInState.SMS_MFA -> println("Please confirm sign-in with SMS.")
                            else -> println("Unsupported sign-in confirmation: " + signInResult.signInState)
                        }
                    })
                }

                override fun onError(e: java.lang.Exception) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.e(TAG, "Sign-in error", e)
                    })
                }
            })
    }

    fun forgotPassword(username: String, onForgotPassword: (success: Boolean) -> Unit) {
        AWSMobileClient.getInstance().forgotPassword(
            "username",
            object : Callback<ForgotPasswordResult> {
                override fun onResult(result: ForgotPasswordResult) {
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.d(TAG, "forgot password state: " + result.state)
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
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.e(TAG, "forgot password error", e)

                    })
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
                        Log.d(TAG, "forgot password state: " + result.state)
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
                    ThreadUtils.runOnUiThread(Runnable {
                        Log.e(TAG, "forgot password error", e)

                    })
                }
            })
    }

    fun signOut(onStateChange: () -> Unit) {
        AWSMobileClient.getInstance()?.signOut()

        onStateChange()
    }

    fun globalSignOut() {
        AWSMobileClient.getInstance()?.signOut(
            SignOutOptions.builder().signOutGlobally(true).build(),
            object : Callback<Void?> {
                override fun onResult(result: Void?) {
                    Log.d(TAG, "signed-out")
                }

                override fun onError(e: java.lang.Exception) {
                    Log.e(TAG, "sign-out error", e)
                }
            })
    }

}