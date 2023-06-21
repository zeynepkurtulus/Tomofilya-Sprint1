package com.sabanciuniv.tomofilyasprint1.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient

class ForgotPasswordViewModel (): ViewModel(){
    private lateinit var ctx: Context
    private val retrofitClient = RetrofitClient(ctx)
    fun setContext(ctx: Context){
        this.ctx = ctx
    }


    fun forgotPassSendVerifyCode(email: String) {
        retrofitClient.forgotPassSendVerifyCode(email)
    }
}