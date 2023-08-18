package com.sabanciuniv.tomofilyasprint1.retrofitHandler

import android.content.Context
import android.content.Intent
import android.util.Log
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.*
import com.sabanciuniv.tomofilyasprint1.model.AuthenticationSocial.AuthenticationLoginDataResponse
import com.sabanciuniv.tomofilyasprint1.model.HomeGetAll.CategoryResponse
import com.sabanciuniv.tomofilyasprint1.model.HomeGetAll.LastVideo
import com.sabanciuniv.tomofilyasprint1.model.UserPasswordReset.UserPasswordResetResponse
import com.sabanciuniv.tomofilyasprint1.model.UserPost.UserPostResponse
import com.sabanciuniv.tomofilyasprint1.model.UserSendVerificationCode.UserSendVerificationCodeResponse
import com.sabanciuniv.tomofilyasprint1.model.UserVerifycode.UserVerifycodeResponse
import com.sabanciuniv.tomofilyasprint1.network.*
import com.sabanciuniv.tomofilyasprint1.network.Constants.api
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(private val ctx: Context) {
    //val apiRequest: APIRequest


    // A function that generates an API request
    fun createAPIRequest(): APIRequest {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.baseURL)
            .build()

        return retrofit.create(APIRequest::class.java)
    }



    fun createAPIRequestWithToken(token: String): APIRequest {
        val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            Log.d("API Request", "URL: ${newRequest.url}")
            Log.d("API Request", "Headers: ${newRequest.headers}")
            chain.proceed(newRequest)
        }.build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.baseURL)
            .client(okHttpClient)
            .build()

        return retrofit.create(APIRequest::class.java)
    }
}
