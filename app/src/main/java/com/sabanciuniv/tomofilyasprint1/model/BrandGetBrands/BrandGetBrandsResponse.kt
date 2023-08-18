package com.sabanciuniv.tomofilyasprint1.model.BrandGetBrands

data class BrandGetBrandsResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)