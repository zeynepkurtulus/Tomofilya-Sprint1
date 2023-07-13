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
    private lateinit var retrofitClient: RetrofitClient
    fun setContext(ctx: Context){
        this.ctx = ctx
        retrofitClient = RetrofitClient(ctx)
    }


    fun resetPassword(email: String, code: String, password: String) {
        try
        {
            val retrofitBuilder = retrofitClient.createAPIRequest()
            val request = UserResetPasswordRequest(email, code, password)
            val retrofitData = retrofitBuilder.resetPassword(request)
            retrofitData.enqueue(object : Callback<UserPasswordResetResponse> {
                override fun onResponse(
                    call: Call<UserPasswordResetResponse>,
                    response: Response<UserPasswordResetResponse>
                ) {
                    Log.e("verification response:", "retrieving body")
                    Log.e("verification r body", response.raw().toString())
                    val responseBody = response.body()
                    Log.e("verification success", responseBody?.success.toString())
                    Log.e("verification message", responseBody?.message.toString())

                    if (responseBody?.success == true) {
                        val intent = Intent(ctx, LoginActivity::class.java)
                        ctx.startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<UserPasswordResetResponse>, t: Throwable) {
                    Log.e("verification error: ", t.toString())
                }
            })

        }

        catch (e: Exception) {
            Log.e("Login api error: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }




    }


}