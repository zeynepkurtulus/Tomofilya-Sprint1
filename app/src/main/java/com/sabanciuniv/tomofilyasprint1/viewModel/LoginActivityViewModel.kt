package com.sabanciuniv.tomofilyasprint1.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.WelcomePage
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
    private val retrofitClient = RetrofitClient(ctx)
    fun setContext(ctx: Context){
        this.ctx = ctx
    }



    fun loginUser(login_email: String, login_pass: String) {
        retrofitClient.loginUser(login_email, login_pass)
    }


}