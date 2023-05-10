package com.sabanciuniv.tomofilyasprint1.ViewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.LoginActivity
import com.sabanciuniv.tomofilyasprint1.data.api.APIRequest
import com.sabanciuniv.tomofilyasprint1.data.api.Constants
import com.sabanciuniv.tomofilyasprint1.data.api.UserResetPasswordRequest
import com.sabanciuniv.tomofilyasprint1.data.UserPasswordReset.UserPasswordResetResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResetPasswordViewModel (): ViewModel() {

    private lateinit var ctx: Context

    fun setContext(ctx: Context){
        this.ctx = ctx
    }


    fun resetPassword(email : String, code : String, pass : String){

        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()
                .create(APIRequest::class.java)



            /*
            val email: String = intent.getStringExtra("email").toString()
            val code : String = intent.getStringExtra("code").toString()
            val pass : String = new_pass.text.toString()

             */
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
                        val intent = Intent(ctx, LoginActivity::class.java)
                        //intent.putExtra("email", email)
                        ctx.startActivity(intent)
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