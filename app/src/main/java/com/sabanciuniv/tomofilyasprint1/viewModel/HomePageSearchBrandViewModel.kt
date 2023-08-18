package com.sabanciuniv.tomofilyasprint1.viewModel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.HomePageSearchBrand
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.model.AuthenticationSocial.AuthenticationLoginDataResponse
import com.sabanciuniv.tomofilyasprint1.network.APIRequest
import com.sabanciuniv.tomofilyasprint1.network.AuthenticationLoginRequest
import com.sabanciuniv.tomofilyasprint1.network.Constants
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomePageSearchBrandViewModel : ViewModel() {
    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient
    fun setContext(ctx: HomePageSearchBrand) {
        this.ctx = ctx
        retrofitClient = RetrofitClient(ctx)
        val email = (ctx as? Activity)?.findViewById<EditText>(R.id.login_email)?.text?.toString()
        val password = (ctx as? Activity)?.findViewById<EditText>(R.id.login_pass)?.text?.toString()
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

    fun getBrands(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("tag", "inside getbrands func")
                val retrofitBuilder = retrofitClient.createAPIRequestWithToken(token)
                val response = retrofitBuilder.getBrands().execute()
                val responseBody = response.body()
                Log.e("tag", "Success Message: ${responseBody?.success}")
                if (response.isSuccessful) {

                    Log.e("tag", "Success Message: ${responseBody?.success}")
                    Log.e("tag", "name: ${responseBody?.data}")

                    val recViewList : MutableList<List<String>> = mutableListOf()
                    if (responseBody?.success == true) {
                        for (responseName in responseBody.data) {
                            val nameObj = listOf<String>(responseName.name, responseName.id.toString())
                            recViewList.add(nameObj)
                           // val nameObj = listOf<String>(responseName.name)
                           // recViewList.add(nameObj)
                            //Log.e("marka", "Name: $name")
                        }


                        withContext(Dispatchers.Main) {
                            // Update UI here after switching to the main thread
                            val recView = (ctx as? Activity)?.findViewById<RecyclerView>(R.id.brandsRecView)
                            val adapter = GetBrandsRecViewAdapter(ctx, recViewList)
                            recView?.adapter = adapter
                            recView?.layoutManager = GridLayoutManager(ctx, 3)
                            //val layoutManager = LinearLayoutManager(ctx)
                            //recView?.layoutManager = layoutManager
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