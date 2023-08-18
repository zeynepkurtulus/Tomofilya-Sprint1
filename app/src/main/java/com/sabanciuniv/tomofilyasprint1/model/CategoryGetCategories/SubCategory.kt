package com.sabanciuniv.tomofilyasprint1.model.CategoryGetCategories

data class SubCategory(
    val iconUrl: Any,
    val id: Int,
    val name: String,
    val parent: Any,
    val parentId: Int,
    val subCategories: List<SubCategoryX>
)