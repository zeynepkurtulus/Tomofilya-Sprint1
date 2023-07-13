package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageItemNotFoundBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageItemRequestBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageItemNotFoundViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageItemRequestViewModel

class HomePageItemRequest : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageItemRequestBinding
    private lateinit var viewModel : HomePageItemRequestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageItemRequestViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page_item_request)



        binding.itemReqCatBtn.setOnClickListener {
            val intent = Intent(this, HomePageCategoryForRequest::class.java)
            startActivity(intent)
            finish()
        }




    }
}