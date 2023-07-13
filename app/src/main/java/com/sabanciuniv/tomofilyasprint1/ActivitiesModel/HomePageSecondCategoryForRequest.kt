package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageCategoryForRequestBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSecondCategoryForRequestBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageCategoryForRequestViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSecondCategoriesSearchViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSecondCategoryForRequestViewModel

class HomePageSecondCategoryForRequest : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageSecondCategoryForRequestBinding
    private lateinit var viewModel : HomePageSecondCategoryForRequestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageSecondCategoryForRequestViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.sabanciuniv.tomofilyasprint1.R.layout.activity_home_page_second_category_for_request)
        binding.homePageSecondCategoryForRequest = viewModel

        binding.btnSave.setOnClickListener {
            val intent = Intent(this, HomePageItemRequest::class.java)
            startActivity(intent)
            finish()
        }

        binding.categoriesSecondBackBtn.setOnClickListener {
            val intent = Intent(this, HomePageCategoryForRequest::class.java)
            startActivity(intent)
            finish()
        }
    }
}