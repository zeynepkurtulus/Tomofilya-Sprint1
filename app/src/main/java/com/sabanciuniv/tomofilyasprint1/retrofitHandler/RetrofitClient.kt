package com.sabanciuniv.tomofilyasprint1.retrofitHandler

import android.content.Context
import android.content.Intent
import android.util.Log
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.*
import com.sabanciuniv.tomofilyasprint1.model.AuthenticationSocial.AuthenticationLoginDataResponse
import com.sabanciuniv.tomofilyasprint1.model.UserPasswordReset.UserPasswordResetResponse
import com.sabanciuniv.tomofilyasprint1.model.UserPost.UserPostResponse
import com.sabanciuniv.tomofilyasprint1.model.UserSendVerificationCode.UserSendVerificationCodeResponse
import com.sabanciuniv.tomofilyasprint1.model.UserVerifycode.UserVerifycodeResponse
import com.sabanciuniv.tomofilyasprint1.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(private val ctx: Context) {
    private val apiRequest: APIRequest

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.baseURL)
            .build()

        apiRequest = retrofit.create(APIRequest::class.java)
    }

    fun verifyUser(email: String, code: String, onResponse: (UserVerifycodeResponse?) -> Unit, onFailure: (Throwable) -> Unit) {
        val request = UserVerifyCodeRequest(email, code)
        val retrofitData = apiRequest.verify(request)

        retrofitData.enqueue(object : Callback<UserVerifycodeResponse> {
            override fun onResponse(call: Call<UserVerifycodeResponse>, response: Response<UserVerifycodeResponse>) {
                onResponse(response.body())
            }

            override fun onFailure(call: Call<UserVerifycodeResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun sendVerificationCode(email: String, onResponse: (UserSendVerificationCodeResponse?) -> Unit, onFailure: (Throwable) -> Unit) {
        apiRequest.userSendVerificationCode(email).enqueue(object : Callback<UserSendVerificationCodeResponse> {
            override fun onResponse(call: Call<UserSendVerificationCodeResponse>, response: Response<UserSendVerificationCodeResponse>) {
                onResponse(response.body())
            }

            override fun onFailure(call: Call<UserSendVerificationCodeResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun emailConfirm(d1: String, d2: String, d3: String, d4: String, email: String) {
        val code: String = d1 + d2 + d3 + d4
        Log.e("ConfirmEmail", "message: $code")
        Log.e("Email is: ", "$email")

        verifyUser(email, code,
            onResponse = { response ->
                Log.e("verification success", response?.success.toString())
                Log.e("verification message", response?.message.toString())

                if (response?.success == true) {
                    startResetPasswordActivity(email, code)
                }
            },
            onFailure = { t ->
                Log.e("verification error: ", t.toString())
            }
        )
    }

    fun forgotPassSendVerifyCodeEmailForgot(email: String) {
        sendVerificationCode(email,
            onResponse = { response ->
                Log.e("verification success", response?.success.toString())
                Log.e("verification message", response?.message.toString())
            },
            onFailure = { t ->
                Log.e("verification error: ", t.toString())
            }
        )
    }

    private fun startResetPasswordActivity(email: String, code: String) {
        val intent = Intent(ctx, ResetPassword::class.java)
        intent.putExtra("email", email)
        intent.putExtra("code", code)
        ctx.startActivity(intent)
    }


    fun forgotPassSendVerifyCode(email: String) {
        sendVerificationCode(email,
            onResponse = { response ->
                Log.e("verification success", response?.success.toString())
                Log.e("verification message", response?.message.toString())

                if (response?.success == true) {
                    Log.e("Success", "Forgot My Password")
                    val intent = Intent(ctx, ConfirmEmailWhenForgotPassword::class.java)
                    intent.putExtra("email", email)
                    ctx.startActivity(intent)
                }
            },
            onFailure = { t ->
                Log.e("verification error: ", t.toString())
            }
        )
    }


    fun loginUser(email: String, password: String) {
        val request = AuthenticationLoginRequest(email, password)
        val retrofitData = apiRequest.login(request)

        retrofitData.enqueue(object : Callback<AuthenticationLoginDataResponse> {
            override fun onResponse(call: Call<AuthenticationLoginDataResponse>, response: Response<AuthenticationLoginDataResponse>) {
                Log.e("verification response:", "retrieving body")
                Log.e("verification r body", response.raw().toString())
                val responseBody = response.body()
                Log.e("verification success", responseBody?.success.toString())
                Log.e("verification message", responseBody?.message.toString())

                if (responseBody?.success == true) {
                    val intent = Intent(ctx, WelcomePage::class.java)
                    ctx.startActivity(intent)
                }
            }

            override fun onFailure(call: Call<AuthenticationLoginDataResponse>, t: Throwable) {
                Log.e("verification error: ", t.toString())
            }
        })
    }


    fun resetPassword(email: String, code: String, password: String) {
        val request = UserResetPasswordRequest(email, code, password)
        val retrofitData = apiRequest.resetPassword(request)

        retrofitData.enqueue(object : Callback<UserPasswordResetResponse> {
            override fun onResponse(call: Call<UserPasswordResetResponse>, response: Response<UserPasswordResetResponse>) {
                Log.e("verification response:", "retrieving body")
                Log.e("verification r body", response.raw().toString())
                val responseBody = response.body()
                Log.e("verification success", responseBody?.success.toString())
                Log.e("verification message", responseBody?.message.toString())

                if (responseBody?.success == true) {
                    val intent = Intent(ctx, LoginActivity::class.java)
                    ctx.startActivity(intent)
                }
            }

            override fun onFailure(call: Call<UserPasswordResetResponse>, t: Throwable) {
                Log.e("verification error: ", t.toString())
            }
        })
    }


    fun registerUser(name: String, email: String, password: String, isOpenNotification: Boolean, onSuccess: () -> Unit) {
        val request = UserPostRequest(name, email, password, isOpenNotification)
        val retrofitData = apiRequest.register(request)

        retrofitData.enqueue(object : Callback<UserPostResponse> {
            override fun onResponse(call: Call<UserPostResponse>, response: Response<UserPostResponse>) {
                Log.e("verification response:", "retrieving body")
                Log.e("verification r body", response.raw().toString())
                val responseBody = response.body()
                Log.e("verification success", responseBody?.success.toString())
                Log.e("verification message", responseBody?.message.toString())

                if (responseBody?.success == true) {
                    val intent = Intent(ctx, ConfirmEmail::class.java)
                    intent.putExtra("email", email)
                    ctx.startActivity(intent)
                    onSuccess()
                }
            }

            override fun onFailure(call: Call<UserPostResponse>, t: Throwable) {
                Log.e("verification error: ", t.toString())
            }
        })
    }
}
