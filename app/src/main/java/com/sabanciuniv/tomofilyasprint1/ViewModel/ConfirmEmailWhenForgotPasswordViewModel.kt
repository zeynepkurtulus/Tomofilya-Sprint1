package com.sabanciuniv.tomofilyasprint1.ViewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.ResetPassword
import com.sabanciuniv.tomofilyasprint1.data.api.APIRequest
import com.sabanciuniv.tomofilyasprint1.data.api.Constants
import com.sabanciuniv.tomofilyasprint1.data.api.UserVerifyCodeRequest
import com.sabanciuniv.tomofilyasprint1.data.UserSendVerificationCode.UserSendVerificationCodeResponse
import com.sabanciuniv.tomofilyasprint1.data.UserVerifycode.UserVerifycodeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfirmEmailWhenForgotPasswordViewModel (): ViewModel() {

    private lateinit var ctx: Context
    private var email: String? = null
    fun setContext(ctx: Context){
        this.ctx = ctx
    }

    private fun setEmail(email : String){
        this.email = email
    }
    private fun getEmail(): String{
        return email!!
    }




    fun emailConfirm(d1 : String, d2 : String, d3: String, d4 : String) {
        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()
                .create(APIRequest::class.java)

            val emailHolder : String = getEmail()
            //val code = intent.getStringExtra("message").toString()


            /*
            val d1 : String = forgot_pass_digit_1.text.toString()
            val d2 : String = forgot_pass_digit_2.text.toString()
            val d3 : String = forgot_pass_digit_3.text.toString()
            val d4 : String = forgot_pass_digit_4.text.toString()

             */
            val code : String =  d1 + d2 + d3 + d4
            Log.e("ConfirmEmail", "message: $code")
            Log.e("Email is: ", "$emailHolder")


            val request = UserVerifyCodeRequest(emailHolder, code)

            val retrofitData = retrofitBuilder.verify(request)
            Log.e("verification process", "going...")

            retrofitData.enqueue(object: Callback<UserVerifycodeResponse> {
                override fun onResponse(call: Call<UserVerifycodeResponse>, response: Response<UserVerifycodeResponse>) {
                    Log.e("verification response:", "retrieving body")
                    Log.e("verification r body", response.raw().toString())
                    val responseBody = response.body()
                    Log.e("verification success", responseBody?.success.toString())
                    Log.e("verification message", responseBody?.message.toString())

                    if (responseBody?.success == true){
                        val intent = Intent(ctx, ResetPassword::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("code", code)
                        ctx.startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<UserVerifycodeResponse>, t: Throwable) {
                    Log.e("verification error: ", t.toString())
                }
            })

        } catch (e: Exception) {
            Log.e("verification step 1: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }
    }


    fun forgotPassSendVerifyCode(email : String){

        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()


            val apiRequest = retrofitBuilder.create(APIRequest::class.java)

            //val request = UserVerifyCodeRequest(email, login_pass)
            Log.e("verification process", "going...")

            apiRequest.userSendVerificationCode(email).enqueue(object:
                Callback<UserSendVerificationCodeResponse> {
                override fun onResponse(call: Call<UserSendVerificationCodeResponse>, response: Response<UserSendVerificationCodeResponse>) {
                    Log.e("verification response:", "retrieving body")
                    Log.e("verification r body", response.raw().toString())
                    val responseBody = response.body()
                    Log.e("verification success", responseBody?.success.toString())
                    Log.e("verification message", responseBody?.message.toString())


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