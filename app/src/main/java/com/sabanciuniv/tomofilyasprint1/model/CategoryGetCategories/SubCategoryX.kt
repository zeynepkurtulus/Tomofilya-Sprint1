package com.sabanciuniv.tomofilyasprint1.model.CategoryGetCategories

data class SubCategoryX(
    val iconUrl: Any,
    val id: Int,
    val name: String,
    val parent: Any,
    val parentId: Int,
    val subCategories: List<SubCategoryXX>
)