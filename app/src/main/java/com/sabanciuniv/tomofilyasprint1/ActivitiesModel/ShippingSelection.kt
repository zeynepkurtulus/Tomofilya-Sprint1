package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.network.SharedDataRepository

class ShippingSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping_selection)
        val sharedDataRepository = SharedDataRepository.getInstance()
        val btnSave = findViewById<Button>(R.id.btnShipmentSave)
        btnSave.setOnClickListener {
            val intent = Intent(this, HomePageItemRequest::class.java)
            intent.putExtra("shipmentType", "Orta Boy")
            val shipmentType = "Orta Boy"
            sharedDataRepository.shipmentType = shipmentType
            startActivity(intent)
            finish()
        }
    }
}