package com.sabanciuniv.tomofilyasprint1.viewModel
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.RegisterProductAddPhotos
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.model.AuthenticationSocial.AuthenticationLoginDataResponse
import com.sabanciuniv.tomofilyasprint1.model.ProductPost.ProductPostResponse
import com.sabanciuniv.tomofilyasprint1.network.*
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomePageItemRequestViewModel : ViewModel() {
    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient

    fun setContext(ctx: Context){
        this.ctx = ctx
        retrofitClient = RetrofitClient(ctx)
    }
    fun loginUser(email: String, password: String, callback: LoginCallBack) {
        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()
                .create(APIRequest::class.java)

            val request = AuthenticationLoginRequest(email, password)
            val retrofitData = retrofitBuilder.login(request)

            retrofitData.enqueue(object : Callback<AuthenticationLoginDataResponse> {
                override fun onResponse(
                    call: Call<AuthenticationLoginDataResponse>,
                    response: Response<AuthenticationLoginDataResponse>
                ) {
                    val responseBody = response.body()

                    if (responseBody?.success == true) {
                        val token = responseBody.data?.accessToken ?: ""
                        callback.onTokenReceived(token)
                    } else {
                        val errorMessage = responseBody?.message ?: "Unknown error"
                        callback.onError(errorMessage)
                    }
                }

                override fun onFailure(call: Call<AuthenticationLoginDataResponse>, t: Throwable) {
                    callback.onError(t.message ?: "Unknown error")
                }
            })
        } catch (e: Exception) {
            callback.onError(e.message ?: "Unknown error")
        }
    }

    fun makeProductRequest(
         stock : Int,
       categoryId : Int,
       brandId : Int?,
        width : Double,
        length : Double,
       weight : Double,
        price: Double,
         title : String?,
         code : String?,
        description : String?,
         isOpenToOffer : Boolean,
         requestId : Int?,
         productStatues : String,
         token : String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("tag", "inside getbrands func")
                val retrofitBuilder = retrofitClient.createAPIRequestWithToken(token)
                val request = GetProductRequest(stock, categoryId, brandId, width, length,
                    weight, price, title, code, description, isOpenToOffer, requestId, productStatues)
                val response = retrofitBuilder.addProducts(request).execute()
                val responseBody = response.body()
                Log.e("tag", "Success Message: ${responseBody?.success}")
                if (response.isSuccessful) {

                    Log.e("tag", "Success Message: ${responseBody?.success}")
                    Log.e("tag", "name: ${responseBody?.data}")

                    val recViewList : MutableList<List<String>> = mutableListOf()
                    if (responseBody?.success == true) {
                        val productId = responseBody.data.toString()
                        val sendBtn = (ctx as? Activity)?.findViewById<Button>(R.id.addProductReqBtn)
                        sendBtn!!.setOnClickListener {
                            val intent = Intent(ctx, RegisterProductAddPhotos::class.java)
                            intent.putExtra("productId", productId)
                            ctx.startActivity(intent)

                        }


                        // The request was successful
                    } else {
                        // The request was not successful
                        Log.e("Login error)", responseBody?.success.toString())
                        Log.e("Login error", responseBody?.message.toString())
                    }
                } else {
                    // Handle the case where the response was not successful
                    Log.e("Login error)", response.message())
                }
            } catch (e: Exception) {
                // Handle the exception case
                Log.e("Login api error: ", "Error occurred: $e")
            }
        }
    }

}