package com.sabanciuniv.tomofilyasprint1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.api.APIRequest
import com.sabanciuniv.tomofilyasprint1.api.Constants
import com.sabanciuniv.tomofilyasprint1.api.UserVerifyCodeRequest
import com.sabanciuniv.tomofilyasprint1.data.UserSendVerificationCode.UserSendVerificationCodeResponse
import com.sabanciuniv.tomofilyasprint1.data.UserVerifycode.UserVerifycodeResponse
import kotlinx.android.synthetic.main.activity_confirm_email.*
import kotlinx.android.synthetic.main.activity_confirm_email_when_forgot_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfirmEmailWhenForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_email_when_forgot_password)

        forgot_pass_btn_send_code.setOnClickListener {
            emailConfirm()
        }

        send_pass_again.setOnClickListener {
            val email: String = intent.getStringExtra("email").toString()
            forgotPassSendVerifyCode(email)
        }
    }

    private fun emailConfirm() {
        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()
                .create(APIRequest::class.java)

            val email: String = intent.getStringExtra("email").toString()
            //val code = intent.getStringExtra("message").toString()

            val d1 : String = forgot_pass_digit_1.text.toString()
            val d2 : String = forgot_pass_digit_2.text.toString()
            val d3 : String = forgot_pass_digit_3.text.toString()
            val d4 : String = forgot_pass_digit_4.text.toString()
            val code : String =  d1 + d2 + d3 + d4
            Log.e("ConfirmEmail", "message: $code")
            Log.e("Email is: ", "$email")


            val request = UserVerifyCodeRequest(email, code)

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
                        val intent = Intent(this@ConfirmEmailWhenForgotPassword, ResetPassword::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("code", code)
                        startActivity(intent)
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


    private fun forgotPassSendVerifyCode(email : String){

        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()


            val apiRequest = retrofitBuilder.create(APIRequest::class.java)

            //val request = UserVerifyCodeRequest(email, login_pass)
            Log.e("verification process", "going...")

            apiRequest.userSendVerificationCode(email).enqueue(object: Callback<UserSendVerificationCodeResponse> {
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