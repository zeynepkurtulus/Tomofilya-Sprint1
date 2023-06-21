package com.sabanciuniv.tomofilyasprint1.network

import com.sabanciuniv.tomofilyasprint1.model.AuthenticationSocial.AuthenticationLoginDataResponse
import com.sabanciuniv.tomofilyasprint1.model.UserPasswordReset.UserPasswordResetResponse
import com.sabanciuniv.tomofilyasprint1.model.UserPost.UserPostResponse
import com.sabanciuniv.tomofilyasprint1.model.UserSendVerificationCode.UserSendVerificationCodeResponse
import com.sabanciuniv.tomofilyasprint1.model.UserVerifycode.UserVerifycodeResponse

import retrofit2.Call
import retrofit2.http.*

interface APIRequest {


    @POST("User/Post")
    @Headers(Constants.api, Constants.contentType )
    fun register(
        @Body request: UserPostRequest
    ): Call<UserPostResponse>



    @POST("User/Verifycode")
    @Headers(Constants.api, Constants.contentType)
    fun verify(
        @Body request: UserVerifyCodeRequest
    ): Call<UserVerifycodeResponse>


    @POST("Authentication/Login")
    @Headers(Constants.api, Constants.contentType )
    fun login(
        @Body request: AuthenticationLoginRequest
    ): Call<AuthenticationLoginDataResponse>


    @POST("Authentication/Social")
    @Headers(Constants.api, Constants.contentType )
    fun loginWithGoogleApple(
        @Body request: AuthenticationLoginWithGoogleAppleRequest
    ): Call<AuthenticationLoginDataResponse>

    @GET("/User/SendVerificationCode/{email}")
    @Headers(Constants.api, Constants.contentType)
    fun userSendVerificationCode(
        @Path("email") email: String
    ): Call<UserSendVerificationCodeResponse>

    @POST("User/PasswordReset")
    @Headers(Constants.api, Constants.contentType)
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