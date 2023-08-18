package com.sabanciuniv.tomofilyasprint1.viewModel

interface LoginCallBack {
    fun onTokenReceived(token: String)
    fun onError(errorMessage: String)
}