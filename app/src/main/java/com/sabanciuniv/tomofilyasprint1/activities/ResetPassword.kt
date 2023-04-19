package com.sabanciuniv.tomofilyasprint1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.api.APIRequest
import com.sabanciuniv.tomofilyasprint1.api.Constants
import com.sabanciuniv.tomofilyasprint1.api.UserPostRequest
import com.sabanciuniv.tomofilyasprint1.api.UserResetPasswordRequest
import com.sabanciuniv.tomofilyasprint1.data.UserPasswordReset.UserPasswordResetResponse
import com.sabanciuniv.tomofilyasprint1.data.UserPost.UserPostResponse
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_welcome_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        change_pass.setOnClickListener {
            resetPassword()
            finish()
        }

        btb_change_cancel.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


    private fun resetPassword(){

        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()
                .create(APIRequest::class.java)


            val email: String = intent.getStringExtra("email").toString()
            val code : String = intent.getStringExtra("code").toString()
            val pass : String = new_pass.text.toString()
            val request = UserResetPasswordRequest(email, code, pass)

            val retrofitData = retrofitBuilder.resetPassword(request)
            Log.e("verification process", "going...")

            retrofitData.enqueue(object: Callback<UserPasswordResetResponse> {
                override fun onResponse(call: Call<UserPasswordResetResponse>, response: Response<UserPasswordResetResponse>) {
                    Log.e("verification response:", "retrieving body")
                    Log.e("verification r body", response.raw().toString())
                    val responseBody = response.body()
                    Log.e("verification success", responseBody?.success.toString())
                    Log.e("verification message", responseBody?.message.toString())

                    /*
                    val intent = Intent(this@WelcomePage, ConfirmEmail::class.java)
                    intent.putExtra("message", responseBody?.message.toString())
                    startActivity(intent)

                     */
                    if (responseBody?.success == true){
                        val intent = Intent(this@ResetPassword, LoginActivity::class.java)
                        //intent.putExtra("email", email)
                        startActivity(intent)
                    }

                }

                override fun onFailure(call: Call<UserPasswordResetResponse>, t: Throwable) {
                    Log.e("verification error: ", t.toString())
                }
            })

        } catch (e: Exception) {
            Log.e("verification step 1: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }

    }


}