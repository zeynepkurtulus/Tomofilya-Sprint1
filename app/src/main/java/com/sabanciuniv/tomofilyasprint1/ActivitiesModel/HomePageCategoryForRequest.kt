package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageCategoriesSearchBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageCategoryForRequestBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageCategoriesSearchViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageCategoryForRequestViewModel

class HomePageCategoryForRequest : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageCategoryForRequestBinding
    private lateinit var viewModel : HomePageCategoryForRequestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageCategoryForRequestViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page_category_for_request)
        binding.homePageCategoryForRequestViewModel = viewModel


        binding.categoriesBackBtn.setOnClickListener {
            val intent = Intent(this, HomePageItemRequest::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSpareParts.setOnClickListener {
            val intent = Intent(this, HomePageSecondCategoryForRequest::class.java)
            startActivity(intent)
            finish()
        }
    }
}