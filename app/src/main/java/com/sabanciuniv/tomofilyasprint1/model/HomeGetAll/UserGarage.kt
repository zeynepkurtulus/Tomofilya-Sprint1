package com.sabanciuniv.tomofilyasprint1.model.HomeGetAll

data class UserGarage(
    val champaignsCount: Int,
    val commissionRate: Int,
    val garageApprovalStatus: String,
    val garageDetail: GarageDetail,
    val garageEarnings: Int,
    val isUpdated: Boolean,
    val offerNumber: Int,
    val orders: Int,
    val productCount: Int,
    val progressPayment: Int,
    val rejectedProductCount: Int,
    val remainingDay: Int,
    val soldProducts: Int,
    val unUploadedDocuments: List<String>
)