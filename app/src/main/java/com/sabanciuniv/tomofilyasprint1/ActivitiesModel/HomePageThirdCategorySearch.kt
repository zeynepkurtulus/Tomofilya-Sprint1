package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSecondCategorySearchBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageThirdCategorySearchBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.GetCategoriesRecViewAdapter
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSecondCategoriesSearchViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageThirdCategoriesSearchViewModel

class HomePageThirdCategorySearch : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageThirdCategorySearchBinding
    private lateinit var viewModel : HomePageThirdCategoriesSearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageThirdCategoriesSearchViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.sabanciuniv.tomofilyasprint1.R.layout.activity_home_page_third_category_search)
        binding.homePageThirdCategorySearchViewModel = viewModel
        viewModel.getSubCategories(false, 3)

        binding.categoriesSaveBtn.setOnClickListener {
            val intent = Intent(this, HomePageSearchBrand::class.java)
            startActivity(intent)
            finish()
        }
        binding.categoriesThirdBackBtn.setOnClickListener {
            val intent = Intent(this, HomePageSecondCategorySearch::class.java)
            startActivity(intent)
            finish()
        }

    }
}