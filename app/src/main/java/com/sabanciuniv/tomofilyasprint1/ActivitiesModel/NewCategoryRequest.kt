package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageItemRequestBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityNewCategoryRequestBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageItemRequestViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.NewCategoryRequestViewModel

class NewCategoryRequest : AppCompatActivity() {
    private lateinit var binding : ActivityNewCategoryRequestBinding
    private lateinit var viewModel : NewCategoryRequestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(NewCategoryRequestViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_category_request)
        binding.newCategoryRequestViewModel = viewModel

    }
}