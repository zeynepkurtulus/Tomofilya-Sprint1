package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageItemNotFoundBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSearchBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageItemNotFoundViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomeSearchViewModel

class HomePageItemNotFound : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageItemNotFoundBinding
    private lateinit var viewModel : HomePageItemNotFoundViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageItemNotFoundViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_page_item_not_found)
        binding.homePageItemNotFoundViewModel = viewModel
        val searchText = intent.getStringExtra("searchText")
        binding.searchArea.setText(searchText)

        binding.genrateRequest.setOnClickListener {
            val intent = Intent(this, HomePageItemRequest::class.java)
            startActivity(intent)
            finish()
        }
        binding.backBtn.setOnClickListener {
            val intent = Intent(this, HomePageSecondCategoryForRequest::class.java)
            startActivity(intent)
            finish()
        }

    }
}