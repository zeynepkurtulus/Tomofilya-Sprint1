package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageItemNotFoundBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageItemRequestBinding
import com.sabanciuniv.tomofilyasprint1.network.SharedDataRepository
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageItemNotFoundViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageItemRequestViewModel
import com.sabanciuniv.tomofilyasprint1.viewModel.LoginCallBack

class HomePageItemRequest : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageItemRequestBinding
    private lateinit var viewModel : HomePageItemRequestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageItemRequestViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page_item_request)
        binding.homePageItemRequestViewModel = viewModel
        val sharedDataRepository = SharedDataRepository.getInstance()
        val email = sharedDataRepository.email
        val password = sharedDataRepository.password
        val brand = intent.getStringExtra("brandHolder")




        binding.backBtn.setOnClickListener {
            val intent = Intent(this, HomePageSearch::class.java)
            startActivity(intent)
            finish()
        }

        binding.relative1.setOnClickListener {
            binding.relative1.setBackgroundResource(R.drawable.register_button_rounded)
            binding.notUsedTv.setTextColor(Color.BLACK)
            binding.notUsedTv2.setTextColor(Color.BLACK)
            binding.relative2.setBackgroundResource(R.drawable.item_request_category_brand)
            binding.secondHandTv.setTextColor(Color.WHITE)
            binding.secondHandTv2.setTextColor(Color.WHITE)
        }

        binding.relative2.setOnClickListener {
            binding.relative2.setBackgroundResource(R.drawable.register_button_rounded)
            binding.secondHandTv.setTextColor(Color.BLACK)
            binding.secondHandTv2.setTextColor(Color.BLACK)
            binding.relative1.setBackgroundResource(R.drawable.item_request_category_brand)
            binding.notUsedTv.setTextColor(Color.WHITE)
            binding.notUsedTv2.setTextColor(Color.WHITE)
        }

        binding.openForOffer.setOnClickListener {
            binding.openForOffer.setBackgroundResource(R.drawable.register_button_rounded)
            binding.openForOffer.setTextColor(Color.BLACK)
            binding.closedForOffer.setBackgroundResource(R.drawable.item_request_category_brand)
            binding.closedForOffer.setTextColor(Color.WHITE)
        }

        binding.closedForOffer.setOnClickListener {
            binding.closedForOffer.setBackgroundResource(R.drawable.register_button_rounded)
            binding.closedForOffer.setTextColor(Color.BLACK)
            binding.openForOffer.setBackgroundResource(R.drawable.item_request_category_brand)
            binding.openForOffer.setTextColor(Color.WHITE)
        }

        binding.categorySelectionBtn.setOnClickListener {
            val intent = Intent(this, HomePageCategoryForRequest::class.java)
            startActivity(intent)
            finish()
        }

        binding.cargoSelectionBtn.setOnClickListener {
            val intent = Intent(this, ShippingSelection::class.java)
            startActivity(intent)
            finish()
        }

        binding.priceHolder.setOnClickListener {
            val intent = Intent(this, PriceSelection::class.java)
            startActivity(intent)
            finish()
        }
        binding.priceText.text = sharedDataRepository.priceInRequest
        binding.cargoText.text = sharedDataRepository.shipmentType
        binding.brandText.text = brand

        viewModel.loginUser(email, password, object : LoginCallBack {
            override fun onTokenReceived(token: String) {
                if (token.isNotEmpty()) {
                    Log.e("tag", "Token inside activity: $token")
                    viewModel.makeProductRequest(28,642,85,5.0,5.0,5.0, 38.0, "assh", "282",
                        "shah", true, 0, "New", token)
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


    }
}