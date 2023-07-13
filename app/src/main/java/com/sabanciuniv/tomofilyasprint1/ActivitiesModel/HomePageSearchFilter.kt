package com.sabanciuniv.tomofilyasprint1.ActivitiesModel


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageSearchFilterBinding
import com.sabanciuniv.tomofilyasprint1.viewModel.HomePageSearchFilterViewModel


class HomePageSearchFilter : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageSearchFilterBinding
    private lateinit var viewModel : HomePageSearchFilterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomePageSearchFilterViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.sabanciuniv.tomofilyasprint1.R.layout.activity_home_page_search_filter)
        binding.homePageSearchFilterViewModel = viewModel

       binding.filterHolder.setOnClickListener {
            if (binding.filterContentHolder.visibility == View.VISIBLE) {
                binding.filterContentHolder.visibility = View.INVISIBLE
            }
            if (binding.sortingContentHolder.visibility == View.VISIBLE) {
               binding.sortingContentHolder.visibility = View.INVISIBLE
            }
           if (binding.filterContentHolder.visibility == View.INVISIBLE){
               binding.filterContentHolder.visibility = View.VISIBLE
           }


        }
        binding.backBtnForFilter.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
            finish()
        }

        binding.categoryBtn.setOnClickListener {
            val intent = Intent(this, HomePageCategoriesSearch::class.java)
            startActivity(intent)
            finish()
        }

        binding.brandBtn.setOnClickListener {
            val intent = Intent(this, HomePageSearchBrand::class.java)
            startActivity(intent)
            finish()
        }

        binding.priceHighToLow.setOnClickListener {
            if (binding.tickLowToHigh.visibility == View.VISIBLE){
                binding.tickLowToHigh.visibility = View.INVISIBLE
            }

            binding.tickHighToLow.visibility = View.VISIBLE
            binding.priceTxt.text = "En Yüksekten En Düşüğe"
        }



        binding.priceLowToHigh.setOnClickListener {
            if (binding.tickHighToLow.visibility == View.VISIBLE){
                binding.tickHighToLow.visibility = View.INVISIBLE
            }
            binding.tickLowToHigh.visibility = View.VISIBLE
            binding.priceTxt.text = "En Düşükten En Yükseğe"

        }



        binding.btnOldToNew.setOnClickListener {
            if (binding.tickNewToOld.visibility == View.VISIBLE){
                binding.tickNewToOld.visibility = View.INVISIBLE
            }
            binding.tickOldToNew.visibility = View.VISIBLE
            binding.sortingText.text = "Eskiden Yeniye"

        }

        binding.sortingBtnNewToOld.setOnClickListener {
            if (binding.tickOldToNew.visibility == View.VISIBLE){
                binding.tickOldToNew.visibility = View.INVISIBLE
            }

            binding.tickNewToOld.visibility = View.VISIBLE
            binding.sortingText.text = "Yeniden Eskiye"
        }

        binding.sortingOrder.setOnClickListener {
            if (binding.sortingContentHolder.visibility == View.VISIBLE) {
                binding.sortingContentHolder.visibility = View.INVISIBLE
            }

            if (binding.filterContentHolder.visibility == View.VISIBLE){
                binding.filterContentHolder.visibility = View.INVISIBLE
            }

            if (binding.sortingContentHolder.visibility == View.INVISIBLE){
                binding.sortingContentHolder.visibility = View.VISIBLE
            }

        }





        /* USE BELOW IN CASE YOU WANT TO USE A SPINNER FOR THE FILTERING
        val categoriesList = arrayOf<String?>("Araç & Yedek Parça & Aksesuar", "Jant & Lastik",
            "Bakım & Onarım", "Tomoclub & Market & Tekstil", "Ses & Görüntü Sistemleri"
        ,"Garaj Ekipman & Alet")

        val brandsList = arrayOf<String?>("Honda", "Ford", "Volkswagen", "Mercedes","BMW")

        val usageLevelList = arrayOf<String?>("Yeniden Eskiye", "Eskiden Yeniye")

        val priceBandList = arrayOf<String?>("100 TL - 480 TL", "480 TL - 800 TL", "800 TL - 1500 TL")

        val categoriesAdapter =
            ArrayAdapter<Any?>(this, com.sabanciuniv.tomofilyasprint1.R.layout.spinner_item_dropdown, categoriesList)
        categoriesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_list)
        binding.categoriesSpinner.adapter = categoriesAdapter


        val brandAdapter =
            ArrayAdapter<Any?>(this, com.sabanciuniv.tomofilyasprint1.R.layout.spinner_item_dropdown, brandsList)
        brandAdapter.setDropDownViewResource(R.layout.spinner_dropdown_list)
        binding.brandSpinner.adapter = brandAdapter

        val usageLevelAdapter =
            ArrayAdapter<Any?>(this, com.sabanciuniv.tomofilyasprint1.R.layout.spinner_item_dropdown, usageLevelList)
        usageLevelAdapter.setDropDownViewResource(R.layout.spinner_dropdown_list)
        binding.usageLevelSpinner.adapter = usageLevelAdapter

        val priceBandAdapter =
            ArrayAdapter<Any?>(this, com.sabanciuniv.tomofilyasprint1.R.layout.spinner_item_dropdown, priceBandList)
        priceBandAdapter.setDropDownViewResource(R.layout.spinner_dropdown_list)
        binding.priceBandSpinner.adapter = priceBandAdapter

         */
    }


}