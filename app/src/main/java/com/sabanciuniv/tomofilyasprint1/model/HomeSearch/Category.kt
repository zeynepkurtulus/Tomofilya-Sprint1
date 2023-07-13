package com.sabanciuniv.tomofilyasprint1.model.HomeSearch

data class Category(
    val iconUrl: String,
    val id: Int,
    val name: String,
    val parent: String,
    val parentId: Int,
    val subCategories: List<Any>
)