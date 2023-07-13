package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSearchResultsBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSortingBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSearchResultsViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSortingViewModel

class HomePageSorting : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageSortingBinding
    private lateinit var viewModel : HomePageSortingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageSortingViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.sabanciuniv.tomofilyasprint1.R.layout.activity_home_page_sorting)
        binding.homePageSortingViewModel = viewModel

        binding.sortingOrder.setOnClickListener {
            if (binding.sortingContentHolder.visibility == View.VISIBLE) {
                binding.sortingContentHolder.visibility = View.INVISIBLE
            } else {
                binding.sortingContentHolder.visibility = View.VISIBLE
            }
        }

        binding.priceHighToLow.setOnClickListener {
            if (binding.priceLowToHigh.visibility == View.VISIBLE){
                binding.tickLowToHigh.visibility = View.INVISIBLE
            }
            binding.tickHighToLow.visibility = View.VISIBLE

            binding.priceTxt.text = "En Düşükten En Yükseğe"
        }
        binding.priceLowToHigh.setOnClickListener {
            if (binding.priceHighToLow.visibility == View.VISIBLE){
                binding.tickHighToLow.visibility = View.INVISIBLE
            }
            binding.tickLowToHigh.visibility = View.VISIBLE
            binding.priceTxt.text = "En Yüksekten En Düşüğe"
            binding.priceTxt.visibility = View.VISIBLE
        }
        binding.filterHolder.setOnClickListener {
            val intent = Intent(this, HomePageSearchFilter::class.java)
            startActivity(intent)
            finish()
        }

    }
}