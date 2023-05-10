package com.sabanciuniv.tomofilyasprint1.ViewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.ConfirmEmailWhenForgotPassword
import com.sabanciuniv.tomofilyasprint1.data.api.APIRequest
import com.sabanciuniv.tomofilyasprint1.data.api.Constants
import com.sabanciuniv.tomofilyasprint1.data.UserSendVerificationCode.UserSendVerificationCodeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ForgotPasswordViewModel (): ViewModel(){
    private lateinit var ctx: Context

    fun setContext(ctx: Context){
        this.ctx = ctx
    }


    fun forgotPassSendVerifyCode(email : String){

        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()


            val apiRequest = retrofitBuilder.create(APIRequest::class.java)

            //val request = UserVerifyCodeRequest(email, login_pass)
            Log.e("in forgot password ", "going...")

            apiRequest.userSendVerificationCode(email).enqueue(object:
                Callback<UserSendVerificationCodeResponse> {
                override fun onResponse(call: Call<UserSendVerificationCodeResponse>, response: Response<UserSendVerificationCodeResponse>) {
                    Log.e("verification response:", "retrieving body")
                    Log.e("verification r body", response.raw().toString())
                    val responseBody = response.body()
                    Log.e("verification success", responseBody?.success.toString())
                    Log.e("verification message", responseBody?.message.toString())


                    if (responseBody?.success == true){
                        Log.e("Success","Forgot My Password")
                        val intent = Intent(ctx, ConfirmEmailWhenForgotPassword::class.java)
                        intent.putExtra("email", email)
                        ctx.startActivity(intent)
                        //finish()
                    }

                }

                override fun onFailure(call: Call<UserSendVerificationCodeResponse>, t: Throwable) {
                    Log.e("verification error: ", t.toString())
                }
            })

        } catch (e: Exception) {
            Log.e("verification step 1: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }

    }
}