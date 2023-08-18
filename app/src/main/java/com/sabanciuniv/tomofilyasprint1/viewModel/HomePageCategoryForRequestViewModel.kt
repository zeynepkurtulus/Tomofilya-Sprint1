package com.sabanciuniv.tomofilyasprint1.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.HomePageItemRequest
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.HomePageSearchResults
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.model.CategoryGetCategories.CategoryGetCategoriesResponse
import com.sabanciuniv.tomofilyasprint1.model.HomeGetAll.HomeGetAllResponse
import com.sabanciuniv.tomofilyasprint1.model.HomeSearch.HomeSearchResponse
import com.sabanciuniv.tomofilyasprint1.network.GetCategoriesRequest
import com.sabanciuniv.tomofilyasprint1.network.HomeSearchRequest
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback

class HomePageCategoryForRequestViewModel : ViewModel() {
    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient
    private var responseBodyGen : CategoryGetCategoriesResponse? = null


    fun setContext(ctx: Context){
        this.ctx = ctx
        retrofitClient = RetrofitClient(ctx)
    }


    fun getMainCategories(isFilter : Boolean, garageId : Int){
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
                        categoriesHandler()
                        //makeMainCategories()

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

    private fun categoriesHandler(){
        makeMainCategories()
        makeMainCategoryIcons()
    }



    private fun makeMainCategories(){


        val cat1 = (ctx as Activity)?.findViewById<TextView>(R.id.spareText!!)
        val cat2 = (ctx as Activity)?.findViewById<TextView>(R.id.tireText!!)
        val cat3 = (ctx as Activity)?.findViewById<TextView>(R.id.fixText!!)
        val cat4 = (ctx as Activity)?.findViewById<TextView>(R.id.toolText!!)
        val cat5 = (ctx as Activity)?.findViewById<TextView>(R.id.tomoText!!)
        val cat6 = (ctx as Activity)?.findViewById<TextView>(R.id.musicText!!)


        cat1!!.text = responseBodyGen?.data?.get(0)?.name.toString()
        cat1!!.setTextColor(Color.BLACK)
        Log.e("tag", "Cat1 text: " + responseBodyGen?.data?.get(0)?.name.toString())
        cat2!!.text = responseBodyGen?.data?.get(1)?.name.toString()
        Log.e("tag", "Cat1 text: " + responseBodyGen?.data?.get(1)?.name.toString())
        cat3!!.text = responseBodyGen?.data?.get(2)?.name.toString()
        cat4!!.text = responseBodyGen?.data?.get(3)?.name.toString()
        cat5!!.text = responseBodyGen?.data?.get(4)?.name.toString()
        cat6!!.text = responseBodyGen?.data?.get(5)?.name.toString()
    }

    private fun makeMainCategoryIcons(){
        val baseUrl = "https://tomofilyastorage.blob.core.windows.net/categoryicons/"
        val btn1 = (ctx as Activity)?.findViewById<ImageButton>(R.id.btnSpareParts!!)
        val btn2 =  (ctx as Activity)?.findViewById<ImageButton>(R.id.btnTire!!)
        val btn3 =  (ctx as Activity)?.findViewById<ImageButton>(R.id.btnRepair!!)
        val btn4 =  (ctx as Activity)?.findViewById<ImageButton>(R.id.btnGarage!!)
        val btn5 =  (ctx as Activity)?.findViewById<ImageButton>(R.id.btnMarket!!)
        val btn6 =  (ctx as Activity)?.findViewById<ImageButton>(R.id.btnMusic!!)


        val p2 = responseBodyGen?.data?.get(1)?.iconUrl.toString()
        val p3 = responseBodyGen?.data?.get(2)?.iconUrl.toString()
        val p4 = responseBodyGen?.data?.get(3)?.iconUrl.toString()
        val p5 = responseBodyGen?.data?.get(4)?.iconUrl.toString()
        val p6 = responseBodyGen?.data?.get(5)?.iconUrl.toString()


        val url2 = baseUrl +p2
        val url3 = baseUrl + p3
        val url4 = baseUrl + p4
        val url5 = baseUrl + p5
        val url6 = baseUrl + p6

        Picasso.with(ctx).load(url2).
        fit().into(btn2);
        Picasso.with(ctx).load(url3).
        fit().into(btn3);
        Picasso.with(ctx).load(url4).
        fit().into(btn4);
        Picasso.with(ctx).load(url5).
        fit().into(btn5);
        Picasso.with(ctx).load(url6).
        fit().into(btn6);

    }
}