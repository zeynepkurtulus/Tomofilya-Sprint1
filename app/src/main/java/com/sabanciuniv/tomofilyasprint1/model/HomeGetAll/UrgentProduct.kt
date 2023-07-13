package com.sabanciuniv.tomofilyasprint1.model.HomeGetAll

data class UrgentProduct(
    val approve: String,
    val brandName: String,
    val campaign: Campaign,
    val campaignRate: Int,
    val endDate: String,
    val garageId: Int,
    val id: Int,
    val photoUrl: String,
    val price: Int,
    val startDate: String,
    val stock: Int,
    val title: String
)