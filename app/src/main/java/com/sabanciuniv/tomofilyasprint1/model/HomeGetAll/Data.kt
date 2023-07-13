package com.sabanciuniv.tomofilyasprint1.model.HomeGetAll

data class Data(
    val cartCount: Int,
    val categories: List<Category>,
    val garage: Garage,
    val garages: List<GarageX>,
    val lastAnnouncement: LastAnnouncement,
    val lastEvent: LastEvent,
    val lastVideo: LastVideo,
    val popularSearch: List<PopularSearch>,
    val products: List<Product>,
    val randomGlossary: RandomGlossary,
    val tomopuan: Int,
    val urgentProducts: List<UrgentProduct>,
    val userGarage: UserGarage
)