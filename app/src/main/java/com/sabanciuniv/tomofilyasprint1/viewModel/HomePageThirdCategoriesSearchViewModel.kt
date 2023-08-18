package com.sabanciuniv.tomofilyasprint1.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.HomePageItemRequest
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.model.CategoryGetCategories.CategoryGetCategoriesResponse
import com.sabanciuniv.tomofilyasprint1.network.GetCategoriesRequest
import com.sabanciuniv.tomofilyasprint1.network.SharedDataRepository
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import retrofit2.Call
import retrofit2.Callback

class HomePageThirdCategoriesSearchViewModel : ViewModel() {

    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient
    private var responseBodyGen : CategoryGetCategoriesResponse? = null
    private val _selectedBrandText = MutableLiveData<String>()
    val sharedDataRepository = SharedDataRepository.getInstance()
    var brandTxt = sharedDataRepository.brand
    val selectedBrandText: LiveData<String>
        get() = _selectedBrandText

    // Method to handle item click events in the adapter
    fun onItemClick(brandText: String) {
        _selectedBrandText.value = brandText
    }


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
                    val recViewList : MutableList<List<String>> = mutableListOf()
                    if (responseBody?.success == true) {
                        Log.e("Login Succcess", responseBody?.success.toString())
                        val subCategoryList = responseBody?.data?.get(0)?.subCategories?.get(6)?.subCategories?.get(0)?.subCategories
                        val namesList: MutableList<String> = mutableListOf()

                        for (responseObj in subCategoryList!!){
                            val nameObj = listOf<String>(responseObj.name, responseObj.id.toString())
                            Log.e("tag", "Name is: $nameObj")
                            recViewList.add(nameObj)
                        }



                        recViewList.add(namesList)
                        val recView = (ctx as? Activity)?.findViewById<RecyclerView>(R.id.categoriesRecView)

                        var adapter = GetCategoriesRecViewAdapter(ctx, recViewList)
                        recView?.adapter = adapter
                        recView?.layoutManager = GridLayoutManager(ctx, 3)

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


}