package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageCategoriesSearchBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSearchBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageCategoriesSearchViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomeSearchViewModel

class HomePageCategoriesSearch : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageCategoriesSearchBinding
    private lateinit var viewModel : HomePageCategoriesSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageCategoriesSearchViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_page_categories_search)
        binding.homePageCategoriesSearchViewModel = viewModel

        binding.categoriesBackBtn.setOnClickListener {
            val intent = Intent(this, HomePageSearchFilter::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSpareParts.setOnClickListener {
            val intent = Intent(this, HomePageSecondCategorySearch::class.java)
            startActivity(intent)
            finish()
        }


    }
}