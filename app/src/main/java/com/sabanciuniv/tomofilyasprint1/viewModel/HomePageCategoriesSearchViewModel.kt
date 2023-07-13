package com.sabanciuniv.tomofilyasprint1.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient

class HomePageCategoriesSearchViewModel : ViewModel() {

    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient


    fun setContext(ctx: Context){
        this.ctx = ctx
        retrofitClient = RetrofitClient(ctx)
    }
}