package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.ViewModel.ForgotPasswordViewModel
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPassword : AppCompatActivity() {
    private lateinit var viewModel : ForgotPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        val typeFace : Typeface = Typeface.createFromAsset(assets,"Poppins-Regular.ttf")
        forgot_password.typeface = typeFace
        email.typeface = typeFace
        email_desc.typeface = typeFace
        btn_cancel.typeface = typeFace
        btn_send.typeface = typeFace

        btn_send.setOnClickListener {
            viewModel.forgotPassSendVerifyCode(forgot_pass_email_field.text.toString())
            //forgotPassSendVerifyCode(forgot_pass_email_field.text.toString())
        }

        btn_cancel.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


 /*

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


                    if (responseBody?.success == true){
                        val intent = Intent(this@ForgotPassword, ConfirmEmailWhenForgotPassword::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                        finish()
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

  */
}