package com.sabanciuniv.tomofilyasprint1.model.HomeGetAll

data class LastAnnouncement(
    val buttonName: String,
    val buttonUrl: String,
    val clickUrlCount: Int,
    val date: String,
    val description: String,
    val hasButton: Boolean,
    val id: Int,
    val isPublished: Boolean,
    val name: String,
    val photoUrl: String,
    val showPopup: Boolean,
    val showPopupCount: Int
)