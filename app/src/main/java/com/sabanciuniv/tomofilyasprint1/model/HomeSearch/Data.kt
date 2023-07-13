package com.sabanciuniv.tomofilyasprint1.model.HomeSearch

data class Data(
    val brands: List<Brand>,
    val categories: List<Category>,
    val garages: List<Garage>,
    val pageSize: Int,
    val products: List<Product>
)