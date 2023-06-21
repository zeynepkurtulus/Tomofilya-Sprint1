package com.sabanciuniv.tomofilyasprint1.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.LoginActivity
import com.sabanciuniv.tomofilyasprint1.network.APIRequest
import com.sabanciuniv.tomofilyasprint1.network.Constants
import com.sabanciuniv.tomofilyasprint1.network.UserResetPasswordRequest
import com.sabanciuniv.tomofilyasprint1.model.UserPasswordReset.UserPasswordResetResponse
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResetPasswordViewModel (): ViewModel() {

    private lateinit var ctx: Context
    private val retrofitClient = RetrofitClient(ctx)
    fun setContext(ctx: Context){
        this.ctx = ctx
    }


    fun resetPassword(email: String, code: String, pass: String) {
        retrofitClient.resetPassword(email, code, pass)
    }


}