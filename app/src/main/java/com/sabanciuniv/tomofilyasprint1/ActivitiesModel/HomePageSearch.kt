package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSearchBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSearchResultsBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.HomeSearchViewModel

class HomePageSearch : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageSearchBinding
    private lateinit var viewModel : HomeSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomeSearchViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_page_search)
        binding.homeSearchViewModel = viewModel

        binding.searchBtn.setOnClickListener {
            val text = binding.searchArea.text.toString()
            viewModel.homePageSearch(text)
            /*
            val intent = Intent(this, HomePageSearchResults::class.java)
            intent.putExtra("searchText", binding.searchArea.text.toString())
            startActivity(intent)
            finish()

             */
        }





        binding.backBtn.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
            finish()
        }
    }
}
