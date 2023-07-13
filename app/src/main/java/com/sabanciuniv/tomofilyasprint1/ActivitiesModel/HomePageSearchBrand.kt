package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSearchBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSearchBrandBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSearchBrandViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomeSearchViewModel

class HomePageSearchBrand : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageSearchBrandBinding
    private lateinit var viewModel : HomePageSearchBrandViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageSearchBrandViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_page_search_brand)
        binding.homePageSearchBrandViewModel = viewModel

        binding.brandBackBtn.setOnClickListener {
            val intent = Intent(this, HomePageSearchFilter::class.java)
            startActivity(intent)
            finish()
        }

    }
}