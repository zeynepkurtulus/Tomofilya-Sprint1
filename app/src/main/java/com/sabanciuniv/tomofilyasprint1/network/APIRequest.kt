package com.sabanciuniv.tomofilyasprint1.network

import com.sabanciuniv.tomofilyasprint1.model.AuthenticationSocial.AuthenticationLoginDataResponse
import com.sabanciuniv.tomofilyasprint1.model.BrandGetBrands.BrandGetBrandsResponse
import com.sabanciuniv.tomofilyasprint1.model.CategoryGetCategories.CategoryGetCategoriesResponse
import com.sabanciuniv.tomofilyasprint1.model.HomeGetAll.HomeGetAllResponse
import com.sabanciuniv.tomofilyasprint1.model.HomeSearch.HomeSearchResponse
import com.sabanciuniv.tomofilyasprint1.model.ProductPhotosPost.ProductPhotosPostResponse
import com.sabanciuniv.tomofilyasprint1.model.ProductPost.ProductPostResponse
import com.sabanciuniv.tomofilyasprint1.model.UserPasswordReset.UserPasswordResetResponse
import com.sabanciuniv.tomofilyasprint1.model.UserPost.UserPostResponse
import com.sabanciuniv.tomofilyasprint1.model.UserSendVerificationCode.UserSendVerificationCodeResponse
import com.sabanciuniv.tomofilyasprint1.model.UserVerifycode.UserVerifycodeResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Call
import retrofit2.http.*
import java.io.File

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


    @GET("/Home/GetAll")
    @Headers(Constants.api, Constants.contentType)
    fun homePageGetAllData(
    ): Call<HomeGetAllResponse>

    @POST("/Home/Search")
    @Headers(Constants.api, Constants.contentType)
    fun homeSearch(
        @Body request: HomeSearchRequest
    ): Call<HomeSearchResponse>

    @GET("Brand/GetBrands")
    @Headers(Constants.api, Constants.contentType)
    fun getBrands(
    ):Call<BrandGetBrandsResponse>

    @POST("/Category/GetCategories")
    @Headers(Constants.api, Constants.contentType)
    fun getCategories(
        @Body request: GetCategoriesRequest
    ): Call<CategoryGetCategoriesResponse>

    @POST("/Product/Post")
    @Headers(Constants.api, Constants.contentType)
    fun addProducts(
        @Body request: GetProductRequest
    ): Call<ProductPostResponse>


    @Multipart
    @POST("/Product/ProductPhotosPost")
    @Headers(Constants.api, Constants.multiContentType)
    fun addPhotos(
       //@Header("Content-Disposition") contentDisposition: MultipartBody, // Add this line
       // @Part ("Product") Product : File,
        @Part Product : MultipartBody.Part,
        @Part("ProductId") ProductId: Int,
        //@Part("ProductPhotoId") ProductPhotoId:Int
    ): Call<ProductPostResponse>

    @Multipart
    @POST("/Product/ProductPhotosPost")
    @Headers(Constants.api)
    fun addPhotosWithBoundary(
        @Header("Authorization") bearerToken: String,
        @Part Product: MultipartBody.Part,
        @Part("productID") productId: RequestBody
    ): Call<ProductPhotosPostResponse>


}


//Request Data Classes are below

data class UserPostRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val isOpenNotification: Boolean

)

data class ProductPhotosPostRequest(
    //val photoId : Int,
    val productId : Int,
    val Product : File
)

data class GetCategoriesRequest(
    val isFilter : Boolean,
    val garageId : Int
)

data class GetProductRequest(
    val stock : Int,
    val categoryId : Int,
    val brandId : Int?,
    val width : Double,
    val length : Double,
    val weight : Double,
    val price: Double,
    val title : String?,
    val code : String?,
    val description : String?,
    val isOpenToOffer : Boolean,
    val requestId : Int?,
    val productStatues : String

)


data class UserVerifyCodeRequest(

    val email : String,
    val code : String
)

data class AuthenticationLoginRequest(

    val email : String,
    val password : String
)


data class UserResetPasswordRequest(

    val email : String,
    val code : String,
    val newPassword : String
)

data class HomeSearchRequest(
    val searchText : String,
    val saleStatus: String,
    val approvalStatus  : String,
    val pagingParameters : PagingClass

)

data class PagingClass(
    val pageNumber : Int,
    val pageSize : Int
)

