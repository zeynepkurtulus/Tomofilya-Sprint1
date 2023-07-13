package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSearchResultsBinding
import com.sabanciuniv.tomofilyasprint1.network.PagingClass
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSearchResultsViewModel


class HomePageSearchResults : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageSearchResultsBinding
    private lateinit var viewModel : HomePageSearchResultsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageSearchResultsViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.sabanciuniv.tomofilyasprint1.R.layout.activity_home_page_search_results)
        binding.homeSearchResultsViewModel = viewModel
        val searchText = intent.getStringExtra("searchText")
        binding.searchAreaForResults.setText(searchText)

        /*
        binding.searchAreaForResults.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Get the current text from the EditText
                val searchText = s.toString()
                Log.e("search", "SearchText in on click: " + searchText)
                viewModel.homePageSearch(searchText)

            }

            override fun afterTextChanged(s: Editable?) {

                // Do nothing
            }
        })

         */



        binding.searchBtn.setOnClickListener {
            val text = binding.searchAreaForResults.text.toString()
            if (text.isEmpty()){
                Toast.makeText(this, "Lütfen geçerli bir kelime girin...", Toast.LENGTH_LONG).show()
            }
            else{
                viewModel.homePageSearch(text)
                binding.homePageRecView.visibility = View.VISIBLE
            }


            }

        binding.backBtnForResults.setOnClickListener {
            val intent = Intent(this, HomePageSearch::class.java)
            startActivity(intent)
            finish()
        }




      
    }
}