package com.sabanciuniv.tomofilyasprint1.model.AuthenticationLogin

data class AuthenticationLoginDataResponse(
    val `data`: DataAuthenticationLogin,
    val message: String,
    val success: Boolean
)