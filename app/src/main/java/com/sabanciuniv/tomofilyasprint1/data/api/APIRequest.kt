package com.sabanciuniv.tomofilyasprint1.data.api

import com.sabanciuniv.tomofilyasprint1.data.AuthenticationSocial.AuthenticationLoginDataResponse
import com.sabanciuniv.tomofilyasprint1.data.UserPasswordReset.UserPasswordResetResponse
import com.sabanciuniv.tomofilyasprint1.data.UserPost.UserPostResponse
import com.sabanciuniv.tomofilyasprint1.data.UserSendVerificationCode.UserSendVerificationCodeResponse
import com.sabanciuniv.tomofilyasprint1.data.UserVerifycode.UserVerifycodeResponse

import retrofit2.Call
import retrofit2.http.*

interface APIRequest {



    @POST("User/Post")
    @Headers("apikey: COF40RZ95GBJZ7R08QVJMIDR0TLEJL1DDEXY10K0H8MQ03DJJ8","Content-Type: application/json" )
    fun register(
        @Body request: UserPostRequest
    ): Call<UserPostResponse>



    @POST("User/Verifycode")
    @Headers("apikey: COF40RZ95GBJZ7R08QVJMIDR0TLEJL1DDEXY10K0H8MQ03DJJ8","Content-Type: application/json" )
    fun verify(
        @Body request: UserVerifyCodeRequest
    ): Call<UserVerifycodeResponse>


    @POST("Authentication/Login")
    @Headers("apikey: COF40RZ95GBJZ7R08QVJMIDR0TLEJL1DDEXY10K0H8MQ03DJJ8","Content-Type: application/json" )
    fun login(
        @Body request: AuthenticationLoginRequest
    ): Call<AuthenticationLoginDataResponse>


    @POST("Authentication/Social")
    @Headers("apikey: COF40RZ95GBJZ7R08QVJMIDR0TLEJL1DDEXY10K0H8MQ03DJJ8","Content-Type: application/json" )
    fun loginWithGoogleApple(
        @Body request: AuthenticationLoginWithGoogleAppleRequest
    ): Call<AuthenticationLoginDataResponse>

    @GET("/User/SendVerificationCode/{email}")
    @Headers("apikey: COF40RZ95GBJZ7R08QVJMIDR0TLEJL1DDEXY10K0H8MQ03DJJ8", "Content-Type: application/json")
    fun userSendVerificationCode(
        @Path("email") email: String
    ): Call<UserSendVerificationCodeResponse>

    @POST("User/PasswordReset")
    @Headers("apikey: COF40RZ95GBJZ7R08QVJMIDR0TLEJL1DDEXY10K0H8MQ03DJJ8","Content-Type: application/json" )
    fun resetPassword(
        @Body request: UserResetPasswordRequest
    ): Call<UserPasswordResetResponse>


}

data class UserPostRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val isOpenNotification: Boolean

)


data class UserVerifyCodeRequest(

    val email : String,
    val code : String
)

data class AuthenticationLoginRequest(

    val email : String,
    val password : String
)

data class AuthenticationLoginWithGoogleAppleRequest(

    val token : String,
    val platform : String
)

data class UserSendVerificationCodeRequest(

    val email : String,
    val apiKey : String
)

data class UserResetPasswordRequest(

    val email : String,
    val code : String,
    val newPassword : String
)