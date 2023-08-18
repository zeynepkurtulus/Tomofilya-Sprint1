package com.sabanciuniv.tomofilyasprint1.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.HomePageItemNotFound
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.HomePageItemRequest
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.HomePageSearchResults
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.model.HomeSearch.HomeSearchResponse
import com.sabanciuniv.tomofilyasprint1.network.HomeSearchRequest
import com.sabanciuniv.tomofilyasprint1.network.PagingClass
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import retrofit2.Call
import retrofit2.Callback

class HomeSearchViewModel : ViewModel() {
    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient


    fun setContext(ctx: Context){
        this.ctx = ctx
        retrofitClient = RetrofitClient(ctx)
    }

    fun homePageSearch(
        searchText: String,
        saleStatus: String = "OnSale",
        approvalStatus: String = "Approved",
        pagingParameters: PagingClass = PagingClass(1,50)
    ) {
        try {
            val retrofitBuilder = retrofitClient.createAPIRequest()
            val request = HomeSearchRequest(searchText, saleStatus,
                approvalStatus, pagingParameters)
            val retrofitData = retrofitBuilder.homeSearch(request)
            Log.e("Data", "Body request: " + request.toString())
            retrofitData.enqueue(object: Callback<HomeSearchResponse> {
                override fun onResponse(call: Call<HomeSearchResponse>, response: retrofit2.Response<HomeSearchResponse>) {

                    val responseBody = response.body()
                    if (responseBody?.success == true) {
                        Log.e("Login Succcess", responseBody?.success.toString())
                        val holderlist : MutableList<List<String>> = mutableListOf()
                        for (product in responseBody?.data!!.products){
                            val productObject = listOf<String>(product.brandName.toString(), "ürün")
                            holderlist.add(productObject)
                        }

                        for (garages in responseBody?.data!!.garages){
                            val garagesObject = listOf<String>(garages.userName.toString(), "garaj")
                            holderlist.add(garagesObject)
                        }

                        for (brands in responseBody?.data!!.brands){
                            val brandObject = listOf<String>(brands.name.toString(), "marka")
                            holderlist.add(brandObject)
                        }
                        for (categories in responseBody?.data!!.categories){
                            val catObject = listOf<String>(categories.name.toString(), "kategori")
                            holderlist.add(catObject)
                        }

                        if (holderlist.isEmpty()){
                            val word = (ctx as Activity)?.findViewById<EditText>(R.id.searchArea!!)
                            val searchText = word?.text.toString()
                            val intent = Intent(ctx, HomePageItemRequest::class.java)
                            intent.putExtra("searchText", searchText)
                            ctx.startActivity(intent)
                        }
                        if (holderlist.isNotEmpty()){
                            val word = (ctx as Activity)?.findViewById<EditText>(R.id.searchArea!!)
                            val searchText = word?.text.toString()
                            val intent = Intent(ctx, HomePageSearchResults::class.java)
                            intent.putExtra("searchText", searchText)
                            ctx.startActivity(intent)
                        }

                        // The request was successful
                    } else {

                        // The request was not successful
                        Log.e("Login error)" , responseBody?.success.toString())
                        Log.e("Login error" , responseBody?.message.toString())

                    }
                }

                override fun onFailure(call: Call<HomeSearchResponse>, t: Throwable) {
                    Log.e("Login api error: ", t.toString())
                    // Handle the error here (e.g. log it or display an error message)
                }
            })

        } catch (e: Exception) {
            Log.e("Login api error: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }
    }
}