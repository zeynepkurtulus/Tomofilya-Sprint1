package com.sabanciuniv.tomofilyasprint1.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient

class ConfirmEmailViewModel (): ViewModel(){
    private lateinit var ctx: Context
    private val retrofitClient = RetrofitClient(ctx)
    //private var email: String? = null
    fun setContext(ctx: Context){
        this.ctx = ctx
    }


    fun emailConfirm(d1: String, d2: String, d3: String, d4: String, email: String) {
        val code: String = d1 + d2 + d3 + d4
        Log.e("ConfirmEmail", "message: $code")
        Log.e("Email is: ", "$email")

        retrofitClient.verifyUser(email, code,
            onResponse = { response ->
                Log.e("verification success", response?.success.toString())
                Log.e("verification message", response?.message.toString())
            },
            onFailure = { t ->
                Log.e("verification error: ", t.toString())
            }
        )
    }

    fun forgotPassSendVerifyCode(email: String) {
        retrofitClient.sendVerificationCode(email,
            onResponse = { response ->
                Log.e("verification success", response?.success.toString())
                Log.e("verification message", response?.message.toString())
            },
            onFailure = { t ->
                Log.e("verification error: ", t.toString())
            }
        )
    }



}