package com.sabanciuniv.tomofilyasprint1.model.CategoryGetCategories

data class Data(
    val iconUrl: String,
    val id: Int,
    val name: String,
    val parent: Any,
    val parentId: Any,
    val subCategories: List<SubCategory>
)