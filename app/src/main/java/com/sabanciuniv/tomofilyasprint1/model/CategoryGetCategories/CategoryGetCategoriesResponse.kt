package com.sabanciuniv.tomofilyasprint1.model.CategoryGetCategories

data class CategoryGetCategoriesResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)