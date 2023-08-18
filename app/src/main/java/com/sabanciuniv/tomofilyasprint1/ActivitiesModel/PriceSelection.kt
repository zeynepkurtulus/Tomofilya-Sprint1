package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.network.SharedDataRepository

class PriceSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_selection)
        val sharedDataRepository = SharedDataRepository.getInstance()

        val saveBtn = findViewById<Button>(R.id.btnPriceSave)
        val priceEntryField = findViewById<EditText>(R.id.priceEntryField)
        saveBtn.setOnClickListener {
            val intent = Intent(this, HomePageItemRequest::class.java)
            val priceEntered = priceEntryField.text.toString()
            sharedDataRepository.priceInRequest = priceEntered
            Log.e("tag", "Price: $priceEntered")
            intent.putExtra("price", priceEntered)
            startActivity(intent)
            finish()
        }
    }
}