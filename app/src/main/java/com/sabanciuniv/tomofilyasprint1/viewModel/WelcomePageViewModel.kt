package com.sabanciuniv.tomofilyasprint1.viewModel

import android.content.Context
import android.content.Intent
import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.ConfirmEmail
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient



class WelcomePageViewModel : ViewModel() {
    private lateinit var context: Context
    private lateinit var retrofitClient: RetrofitClient
    private val isVerified = MutableLiveData<Boolean>()

    fun sendVerificationCode(context: Context, name: String, email: String, password: String) {
        retrofitClient = RetrofitClient(context)

        retrofitClient.registerUser(name, email, password, true) {
            val intent = Intent(context, ConfirmEmail::class.java)
            intent.putExtra("email", email)
            context.startActivity(intent)
            isVerified.value = true
        }
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun getIsVerified(): LiveData<Boolean> {
        return isVerified
    }
}