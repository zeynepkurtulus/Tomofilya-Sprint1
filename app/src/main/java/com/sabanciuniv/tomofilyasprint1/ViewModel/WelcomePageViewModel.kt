package com.sabanciuniv.tomofilyasprint1.ViewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.ActivitiesModel.ConfirmEmail
import com.sabanciuniv.tomofilyasprint1.data.api.APIRequest
import com.sabanciuniv.tomofilyasprint1.data.api.Constants
import com.sabanciuniv.tomofilyasprint1.data.api.UserPostRequest
import com.sabanciuniv.tomofilyasprint1.data.UserPost.UserPostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WelcomePageViewModel (): ViewModel() {
    private lateinit var ctx: Context

    fun setContext(ctx: Context){
        this.ctx = ctx
    }





    fun sendVerificationCode(name : String, email: String, password : String){


        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()
                .create(APIRequest::class.java)
            val isOpenNotification: Boolean = true // set to true for now

            /*
            val name: String = name_surname_txt.text.toString()
            val email: String = email_txt.text.toString()
            val password: String = password_txt.text.toString()
            val isOpenNotification: Boolean = true // set to true for now

             */

            val request = UserPostRequest(name, email, password, isOpenNotification)

            val retrofitData = retrofitBuilder.register(request)
            Log.e("verification process", "going...")

            retrofitData.enqueue(object: Callback<UserPostResponse> {
                override fun onResponse(call: Call<UserPostResponse>, response: Response<UserPostResponse>) {
                    Log.e("verification response:", "retrieving body")
                    Log.e("verification r body", response.raw().toString())
                    val responseBody = response.body()
                    Log.e("verification success", responseBody?.success.toString())
                    Log.e("verification message", responseBody?.message.toString())

                    /*
                    val intent = Intent(this@WelcomePage, ConfirmEmail::class.java)
                    intent.putExtra("message", responseBody?.message.toString())
                    startActivity(intent)

                     */
                    if (responseBody?.success == true){
                        val intent = Intent(ctx, ConfirmEmail::class.java)
                        intent.putExtra("email", email)
                        ctx.startActivity(intent)
                    }

                }

                override fun onFailure(call: Call<UserPostResponse>, t: Throwable) {
                    Log.e("verification error: ", t.toString())
                }
            })

        } catch (e: Exception) {
            Log.e("verification step 1: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }

    }


    /*
    fun showErrorSnackBar(message : String){
        val snackBar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view // need this to set an individual background color
        snackBarView.setBackgroundColor(ContextCompat.getColor(ctx, R.color.snackbar_error_color))
        snackBar.show()
    }
    fun validateForm(name: TextInputEditText, email: TextInputEditText, password: TextInputEditText): Boolean{

        // has the user entered something or not
        return when{
            TextUtils.isEmpty(name.toString())->{
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email.toString())->{
                showErrorSnackBar("Please enter an email address")
                false
            }
            TextUtils.isEmpty(password.toString())->{
                showErrorSnackBar("Please enter a password")
                false
            }
            else -> {
                true
            }
        }

    }

     */



}