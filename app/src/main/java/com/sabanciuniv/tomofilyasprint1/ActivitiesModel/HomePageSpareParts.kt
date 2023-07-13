package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSearchBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSparePartsBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSearchFilterViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSparePartsViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomeSearchViewModel

class HomePageSpareParts : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageSparePartsBinding
    private lateinit var viewModel : HomePageSparePartsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageSparePartsViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.sabanciuniv.tomofilyasprint1.R.layout.activity_home_page_spare_parts)
        binding.homePageSparePartsViewModel = viewModel

    }
}