package com.sabanciuniv.tomofilyasprint1.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.ConfirmEmailWhenForgotPassword
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.LoginActivity
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.ResetPassword
import com.sabanciuniv.tomofilyasprint1.model.UserSendVerificationCode.UserSendVerificationCodeResponse
import com.sabanciuniv.tomofilyasprint1.model.UserVerifycode.UserVerifycodeResponse
import com.sabanciuniv.tomofilyasprint1.network.UserVerifyCodeRequest
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmEmailViewModel (): ViewModel(){
    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient
    //private var email: String? = null
    fun setContext(ctx: Context){
        this.ctx = ctx
        retrofitClient =  RetrofitClient(ctx)
    }




    fun emailConfirm(d1: String, d2: String, d3: String, d4: String, email: String) {
        val code: String = d1 + d2 + d3 + d4
        Log.e("ConfirmEmail", "message: $code")
        Log.e("Email is: ", "$email")

        verifyUser(email, code)
    }

    fun forgotPassSendVerifyCode(email: String) {
        try {
            val retrofitBuilder = retrofitClient.createAPIRequest()
            val retrofitData = retrofitBuilder.userSendVerificationCode(email)
            retrofitData.enqueue(object : Callback<UserSendVerificationCodeResponse> {
                override fun onResponse(call: Call<UserSendVerificationCodeResponse>, response: Response<UserSendVerificationCodeResponse>
                ) {
                    val responseBody = response.body()
                    Log.e("response body" , "Response body: " + responseBody.toString())

                    if(responseBody?.success  == true){
                        Log.e("email","Email is: " + email)
                        startResetPasswordActivity()
                    }
                    else{
                        Log.e("Login error)" , responseBody?.success.toString())
                        Log.e("Login error)" , responseBody?.message.toString())

                    }
                }

                override fun onFailure(call: Call<UserSendVerificationCodeResponse>, t: Throwable) {
                    Log.e("Login error: ", t.toString())
                }
            })

        } catch (e: Exception) {
            Log.e("Login api error: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }
    }


    fun verifyUser(
        email: String,
        code: String,

        ) {
        try {
            val retrofitBuilder = retrofitClient.createAPIRequest()
            val request = UserVerifyCodeRequest(email, code)
            val retrofitData = retrofitBuilder.verify(request)

            retrofitData.enqueue(object : Callback<UserVerifycodeResponse> {
                override fun onResponse(
                    call: Call<UserVerifycodeResponse>,
                    response: Response<UserVerifycodeResponse>
                ) {
                    val responseBody = response.body()
                    Log.d("response body" , responseBody.toString())

                    if(responseBody?.success  == true){
                        val intent = Intent(ctx, ResetPassword::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("code", code)
                        ctx.startActivity(intent)
                    }
                    else{
                        Log.e("Login error)" , responseBody?.success.toString())
                        Log.e("Login error)" , responseBody?.message.toString())

                    }
                }

                override fun onFailure(call: Call<UserVerifycodeResponse>, t: Throwable) {
                    Log.e("Login error: ", t.toString())
                }
            })
        }
        catch (e: Exception) {
            Log.e("Login api error: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }

    }

    private fun startResetPasswordActivity() {
        val intent = Intent(ctx, LoginActivity::class.java)
        ctx.startActivity(intent)
    }


}