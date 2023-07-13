package com.sabanciuniv.tomofilyasprint1.model.HomeSearch

data class Product(
    val approvalStatus: String,
    val brandName: String,
    val campaign: Campaign,
    val description: String,
    val garageId: Int,
    val id: Int,
    val photoUrl: String,
    val price: Double,
    val saleStatus: String,
    val title: String
)