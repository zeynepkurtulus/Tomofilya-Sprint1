package com.sabanciuniv.tomofilyasprint1.viewModel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.model.CategoryGetCategories.CategoryGetCategoriesResponse
import com.sabanciuniv.tomofilyasprint1.network.GetCategoriesRequest
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import retrofit2.Call
import retrofit2.Callback

class HomePageSecondCategoryForRequestViewModel : ViewModel() {
    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient
    private var responseBodyGen : CategoryGetCategoriesResponse? = null


    fun setContext(ctx: Context){
        this.ctx = ctx
        retrofitClient = RetrofitClient(ctx)
    }

    fun getSubCategories(isFilter : Boolean, garageId : Int){
        try {
            val retrofitBuilder = retrofitClient.createAPIRequest()
            val request = GetCategoriesRequest(isFilter, garageId)
            val retrofitData = retrofitBuilder.getCategories(request)
            Log.e("Data", "Body request: " + request.toString())
            retrofitData.enqueue(object: Callback<CategoryGetCategoriesResponse> {
                override fun onResponse(call: Call<CategoryGetCategoriesResponse>, response: retrofit2.Response<CategoryGetCategoriesResponse>) {

                    val responseBody = response.body()
                    responseBodyGen = responseBody
                    if (responseBody?.success == true) {
                        Log.e("Login Succcess", responseBody?.success.toString())
                        //categoriesHandler()
                        makeSubCategories()

                        // The request was successful
                    } else {

                        // The request was not successful
                        Log.e("Login error)" , responseBody?.success.toString())
                        Log.e("Login error" , responseBody?.message.toString())

                    }
                }

                override fun onFailure(call: Call<CategoryGetCategoriesResponse>, t: Throwable) {
                    Log.e("Login api error: ", t.toString())
                    // Handle the error here (e.g. log it or display an error message)
                }
            })

        } catch (e: Exception) {
            Log.e("Login api error: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }
    }

    private fun makeSubCategories(){
        val sub1 = (ctx as Activity)?.findViewById<TextView>(R.id.btnAuto)
        val sub2 = (ctx as Activity)?.findViewById<TextView>(R.id.btnCarTypes)
        val sub3 = (ctx as Activity)?.findViewById<TextView>(R.id.btnMotorbike)
        val sub4 = (ctx as Activity)?.findViewById<TextView>(R.id.btnCommercialCars)
        val sub5 = (ctx as Activity)?.findViewById<TextView>(R.id.btnMarket)
        val sub6 = (ctx as Activity)?.findViewById<TextView>(R.id.btnClassicalCars)
        val sub7 = (ctx as Activity)?.findViewById<TextView>(R.id.btnKaravan)
        val sub8 = (ctx as Activity)?.findViewById<TextView>(R.id.btnSpecialCars)

        sub1?.text = responseBodyGen?.data?.get(0)?.subCategories?.get(0)?.name.toString()
        sub2?.text = responseBodyGen?.data?.get(0)?.subCategories?.get(1)?.name.toString()
        sub3?.text = responseBodyGen?.data?.get(0)?.subCategories?.get(2)?.name.toString()
        sub4?.text = responseBodyGen?.data?.get(0)?.subCategories?.get(3)?.name.toString()
        sub5?.text = responseBodyGen?.data?.get(0)?.subCategories?.get(4)?.name.toString()
        sub6?.text = responseBodyGen?.data?.get(0)?.subCategories?.get(5)?.name.toString()
        sub7?.text = responseBodyGen?.data?.get(0)?.subCategories?.get(6)?.name.toString()
        sub8?.text = responseBodyGen?.data?.get(0)?.subCategories?.get(7)?.name.toString()
    }
}