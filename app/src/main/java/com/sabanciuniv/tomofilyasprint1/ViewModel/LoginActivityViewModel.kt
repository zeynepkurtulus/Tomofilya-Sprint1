package com.sabanciuniv.tomofilyasprint1.ViewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.WelcomePage
import com.sabanciuniv.tomofilyasprint1.data.api.APIRequest
import com.sabanciuniv.tomofilyasprint1.data.api.AuthenticationLoginRequest
import com.sabanciuniv.tomofilyasprint1.data.api.Constants
import com.sabanciuniv.tomofilyasprint1.data.AuthenticationSocial.AuthenticationLoginDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivityViewModel (): ViewModel(){

    private lateinit var ctx: Context

    fun setContext(ctx: Context){
        this.ctx = ctx
    }



    fun loginUser(login_email : String, login_pass : String){

        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()
                .create(APIRequest::class.java)

            /*
            val login_email : String = login_email.text.toString()
            val login_pass : String = login_pass.text.toString()

             */


            val request = AuthenticationLoginRequest(login_email, login_pass)

            val retrofitData = retrofitBuilder.login(request)
            Log.e("verification process", "going...")

            retrofitData.enqueue(object: Callback<AuthenticationLoginDataResponse> {
                override fun onResponse(call: Call<AuthenticationLoginDataResponse>, response: Response<AuthenticationLoginDataResponse>) {
                    Log.e("verification response:", "retrieving body")
                    Log.e("verification r body", response.raw().toString())
                    val responseBody = response.body()
                    Log.e("verification success", responseBody?.success.toString())
                    Log.e("verification message", responseBody?.message.toString())


                    if (responseBody?.success == true){
                        val intent = Intent(ctx, WelcomePage::class.java)
                        ctx.startActivity(intent)
                        //finish()
                    }

                }

                override fun onFailure(call: Call<AuthenticationLoginDataResponse>, t: Throwable) {
                    Log.e("verification error: ", t.toString())
                }
            })

        } catch (e: Exception) {
            Log.e("verification step 1: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }

    }


}