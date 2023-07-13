package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageBinding
import com.sabanciuniv.tomofilyasprint1.network.PagingClass
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSearchFilterViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageViewModel

class HomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var viewModel: HomePageViewModel
    private lateinit var eventNameTxt: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_page)
        binding.homePageViewModel = viewModel
        binding.btnAnnounceHeader.text = viewModel.announceBtnName.value ?: "No announcement"
        viewModel.getData()

        binding.searchArea.setOnClickListener {
            val intent = Intent(this, HomePageSearch::class.java)
            startActivity(intent)
            finish()
        }


        binding.button1.setOnClickListener {
            val intent = Intent(this, HomePageSearchFilter::class.java)
            startActivity(intent)
            finish()
        }

     binding.searchBtn.setOnClickListener {
            Log.e("tag", "Insinde set on click")
            val intent = Intent(this, HomePageSearch::class.java)
            startActivity(intent)
            finish()
        }

    }

}
