package com.sabanciuniv.tomofilyasprint1.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.*
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.network.APIRequest
import com.sabanciuniv.tomofilyasprint1.network.AuthenticationLoginRequest
import com.sabanciuniv.tomofilyasprint1.network.Constants
import com.sabanciuniv.tomofilyasprint1.model.AuthenticationSocial.AuthenticationLoginDataResponse
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivityViewModel (): ViewModel(){

    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient

    fun setContext(ctx: Context){
        this.ctx = ctx
        retrofitClient = RetrofitClient(ctx)
    }

    fun loginUser(email: String, password: String) {
        try {
            val retrofitBuilder = retrofitClient.createAPIRequest()
            val request = AuthenticationLoginRequest(email, password)
            val retrofitData = retrofitBuilder.login(request)
            Log.e("Home process", "going...")
            retrofitData.enqueue(object: Callback<AuthenticationLoginDataResponse> {
                override fun onResponse(call: Call<AuthenticationLoginDataResponse>, response: Response<AuthenticationLoginDataResponse>) {
                    val responseBody = response.body()
                    Log.d("response body" , responseBody.toString())
                    if(responseBody?.success  == true){
                        Constants.TOKEN = responseBody.data.accessToken
                        //TODO get the daha at place it
                        val intent = Intent(ctx, HomePage::class.java)
                        ctx.startActivity(intent)
                    }
                    else{
                        Log.e("Login error)" , responseBody?.success.toString())
                        Log.e("Login error)" , responseBody?.message.toString())
                        val errorMessage = responseBody?.message.toString()
                        Toast.makeText(ctx, errorMessage, Toast.LENGTH_LONG).show()

                    }
                }

                override fun onFailure(call: Call<AuthenticationLoginDataResponse>, t: Throwable) {
                    Log.e("Login error: ", t.toString())
                }
            })

        } catch (e: Exception) {
            Log.e("Login api error: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }
    }



}