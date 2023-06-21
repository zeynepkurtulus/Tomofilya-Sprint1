package com.sabanciuniv.tomofilyasprint1.model.AuthenticationSocial

data class AuthenticationLoginDataResponse(
    val `data`: DataAuthenticationLogin,
    val message: String,
    val success: Boolean
)