package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSearchResultsBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSecondCategorySearchBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSearchResultsViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSecondCategoriesSearchViewModel

class HomePageSecondCategorySearch : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageSecondCategorySearchBinding
    private lateinit var viewModel : HomePageSecondCategoriesSearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageSecondCategoriesSearchViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.sabanciuniv.tomofilyasprint1.R.layout.activity_home_page_second_category_search)
        binding.homePageSecondCategorySearchViewModel = viewModel

        binding.relative7.setOnClickListener {
            val intent = Intent(this, HomePageThirdCategorySearch::class.java)
            startActivity(intent)
            finish()
        }
        binding.categoriesSecondBackBtn.setOnClickListener {
            val intent = Intent(this, HomePageCategoriesSearch::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnKaravan.setOnClickListener {
            val intent = Intent(this, HomePageThirdCategorySearch::class.java)
            startActivity(intent)
            finish()
        }

    }
}