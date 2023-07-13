package com.sabanciuniv.tomofilyasprint1.model.HomeGetAll

data class CategoryResponse(
    val categories: List<Category>
)


data class Category(
    val iconUrl: String,
    val id: Int,
    val name: String,
    val parent: String,
    val parentId: Int,
    val subCategories: List<Any?>
)