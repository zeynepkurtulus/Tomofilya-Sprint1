package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSearchBrandBinding
import com.sabanciuniv.tomofilyasprint1.network.SharedDataRepository
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSearchBrandViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.LoginCallBack

class HomePageSearchBrand : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageSearchBrandBinding
    private lateinit var viewModel : HomePageSearchBrandViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageSearchBrandViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_page_search_brand)
        binding.homePageSearchBrandViewModel = viewModel
        val sharedDataRepository = SharedDataRepository.getInstance()
        val email = sharedDataRepository.email
        val password = sharedDataRepository.password
        Log.e("tag", "email inside activity: " + email)
        Log.e("tag", "pass inside activity: " + password)

            viewModel.loginUser(email, password, object : LoginCallBack {
                override fun onTokenReceived(token: String) {
                    if (token.isNotEmpty()) {
                        Log.e("tag", "Token inside activity: $token")
                        viewModel.getBrands(token)
                    } else {
                        // Handle the case where the token is empty or login failed
                        Log.e("tag", "Token is empty or login failed.")
                    }
                }

                override fun onError(errorMessage: String) {
                    // Handle the error case
                    Log.e("tag", "Login error: $errorMessage")
                }
            })

        binding.btnSaveBrand.setOnClickListener {
            val intent = Intent(this, HomePageItemRequest::class.java)
            startActivity(intent)
            finish()
        }



        binding.brandBackBtn.setOnClickListener {
            val intent = Intent(this, HomePageSearchFilter::class.java)
            startActivity(intent)
            finish()
        }
        binding.generateBrandRequestBtn.setOnClickListener {
            val intent = Intent(this, HomePageItemNotFound::class.java)
            startActivity(intent)
            finish()
        }

    }
}