package com.sabanciuniv.tomofilyasprint1.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Response
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.HomePageThirdCategorySearch
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.model.HomeSearch.HomeSearchResponse
import com.sabanciuniv.tomofilyasprint1.network.HomeSearchRequest
import com.sabanciuniv.tomofilyasprint1.network.PagingClass
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import retrofit2.Call
import retrofit2.Callback

class HomePageSearchResultsViewModel : ViewModel() {
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
                        val recViewList : MutableList<List<String>> = mutableListOf()
                        for (product in responseBody?.data!!.products){
                            val productObject = listOf<String>(product.brandName.toString(), "ürün")
                            recViewList.add(productObject)
                            Log.e("marka", "product: " + productObject[0].toString())
                        }

                        for (garages in responseBody?.data!!.garages){
                            val garagesObject = listOf<String>(garages.userName.toString(), "garaj")
                            recViewList.add(garagesObject)
                            Log.e("marka", "garages: " + garagesObject[0].toString())
                        }

                        for (brands in responseBody?.data!!.brands){
                            val brandObject = listOf<String>(brands.name.toString(), "marka")
                            recViewList.add(brandObject)
                            Log.e("marka", "brands: " + brandObject[0].toString())
                        }
                        for (categories in responseBody?.data!!.categories){
                            val catObject = listOf<String>(categories.name.toString(), "kategori")
                            recViewList.add(catObject)
                            Log.e("categories",  "categories: " + catObject[0].toString())
                        }

                        if (recViewList.isEmpty()){
                            val intent = Intent(ctx,HomePageThirdCategorySearch::class.java)
                            ctx.startActivity(intent)
                        }
                        val recView = (ctx as? Activity)?.findViewById<RecyclerView>(R.id.homePageRecView)
                        val adapter = HomePageRecViewAdapter(ctx, recViewList)
                        recView?.adapter = adapter
                        val layoutManager = LinearLayoutManager(ctx)
                        recView?.layoutManager = layoutManager

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