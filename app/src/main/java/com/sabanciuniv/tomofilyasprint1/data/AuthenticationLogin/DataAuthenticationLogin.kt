package com.sabanciuniv.tomofilyasprint1.data.AuthenticationLogin

data class DataAuthenticationLogin(
    val accessToken: String,
    val expiration: String,
    val fullName: String,
    val isSocialMediaAccount: Boolean,
    val refreshToken: String,
    val userId: Int
)