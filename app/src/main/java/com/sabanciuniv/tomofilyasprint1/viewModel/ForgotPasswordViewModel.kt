package com.sabanciuniv.tomofilyasprint1.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.ConfirmEmailWhenForgotPassword
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.HomePage
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.ResetPassword
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.model.UserSendVerificationCode.UserSendVerificationCodeResponse
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordViewModel (): ViewModel(){
    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient
    fun setContext(ctx: Context){
        this.ctx = ctx
        retrofitClient = RetrofitClient(ctx)
    }




/*
    fun forgotPassSendVerifyCode(email: String) {
        retrofitClient.forgotPassSendVerifyCode(email)
        Log.e("Success", "Forgot My Password")
        val intent = Intent(ctx, ConfirmEmailWhenForgotPassword::class.java)
        intent.putExtra("email", email)
        ctx.startActivity(intent)
    }

 */


    fun forgotPassSendVerifyCode(email: String) {
        try {
            val retrofitBuilder = retrofitClient.createAPIRequest()
            val retrofitData = retrofitBuilder.userSendVerificationCode(email) .enqueue(object : Callback<UserSendVerificationCodeResponse> {
                override fun onResponse(
                    call: Call<UserSendVerificationCodeResponse>,
                    response: Response<UserSendVerificationCodeResponse>
                ) {
                    val responseBody = response.body()
                    Log.e("response body" , "Response body: " + responseBody.toString())

                    if(responseBody?.success  == true){
                        //TODO get the daha at place it
                        Log.e("email","Email is: " + email)
                        startResetPasswordActivity(email)
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


    private fun startResetPasswordActivity(email: String) {
        val intent = Intent(ctx, ConfirmEmailWhenForgotPassword::class.java)
        intent.putExtra("email", email)
        ctx.startActivity(intent)
    }


}