package com.sabanciuniv.tomofilyasprint1.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient

class ConfirmEmailWhenForgotPasswordViewModel (): ViewModel() {

    private lateinit var ctx: Context
    private val retrofitClient = RetrofitClient(ctx)
    //private var email: String? = null
    fun setContext(ctx: Context){
        this.ctx = ctx
    }


    fun emailConfirm(d1: String, d2: String, d3: String, d4: String, email: String) {

        retrofitClient.emailConfirm(d1, d2, d3, d4, email)
    }

    fun forgotPassSendVerifyCode(email: String) {
        retrofitClient.forgotPassSendVerifyCodeEmailForgot(email)
    }



}