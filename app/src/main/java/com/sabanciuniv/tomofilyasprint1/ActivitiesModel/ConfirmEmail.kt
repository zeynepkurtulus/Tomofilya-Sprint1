package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.ViewModel.ConfirmEmailViewModel
import kotlinx.android.synthetic.main.activity_confirm_email.*

class ConfirmEmail : AppCompatActivity() {
    private lateinit var viewModel : ConfirmEmailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ConfirmEmailViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_email)
        btn_send_code.setOnClickListener {
            val d1 : String = digit_1.text.toString()
            val d2 : String = digit_2.text.toString()
            val d3 : String = digit_3.text.toString()
            val d4 : String = digit_4.text.toString()
            val email: String = intent.getStringExtra("email").toString()
            viewModel.emailConfirm(d1,d2,d3,d4,email)
            //emailConfirm()
        }
        original_resend_pass.setOnClickListener {

            val email: String = intent.getStringExtra("email").toString()
            viewModel.forgotPassSendVerifyCode(email)
            //forgotPassSendVerifyCode(email)
        }


    }

    /*

    private fun emailConfirm() {
        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()
                .create(APIRequest::class.java)

            val email: String = intent.getStringExtra("email").toString()
            //val code = intent.getStringExtra("message").toString()

            val d1 : String = digit_1.text.toString()
            val d2 : String = digit_2.text.toString()
            val d3 : String = digit_3.text.toString()
            val d4 : String = digit_4.text.toString()
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


     */


}