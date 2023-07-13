package com.sabanciuniv.tomofilyasprint1.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.ConfirmEmail
import com.sabanciuniv.tomofilyasprint1.model.UserPost.UserPostResponse
import com.sabanciuniv.tomofilyasprint1.network.UserPostRequest
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WelcomePageViewModel : ViewModel() {
    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient
    private val isVerified = MutableLiveData<Boolean>()
    fun setContext(context: Context) {
        this.ctx = context
        retrofitClient = RetrofitClient(ctx)

    }




    fun registerUser(
        name: String,
        email: String,
        password: String,
        isOpenNotification: Boolean,
    ) {

        try{
            val retrofitBuilder = retrofitClient.createAPIRequest()
            val request = UserPostRequest(name, email, password, isOpenNotification)
            val retrofitData = retrofitBuilder.register(request)

            retrofitData.enqueue(object : Callback<UserPostResponse> {
                override fun onResponse(
                    call: Call<UserPostResponse>,
                    response: Response<UserPostResponse>
                ) {
                    Log.e("verification response:", "retrieving body")
                    Log.e("verification r body", response.raw().toString())
                    val responseBody = response.body()
                    Log.e("verification success", responseBody?.success.toString())
                    Log.e("verification message", responseBody?.message.toString())

                    if (responseBody?.success == true) {
                        val intent = Intent(ctx, ConfirmEmail::class.java)
                        intent.putExtra("email", email)
                        ctx.startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<UserPostResponse>, t: Throwable) {
                    Log.e("verification error: ", t.toString())
                }
            })

        }

        catch (e: Exception) {
            Log.e("Login api error: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }

    }

}