package com.sabanciuniv.tomofilyasprint1.model.HomeGetAll

data class LastVideo(
    val clickUrlCount: Int,
    val date: String,
    val id: Int,
    val isPublished: Boolean,
    val showPopup: Boolean,
    val showPopupCount: Int,
    val title: String,
    val videoUrl: String
)