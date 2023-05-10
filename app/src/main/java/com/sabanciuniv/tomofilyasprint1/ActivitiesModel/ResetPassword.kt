package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.ViewModel.ResetPasswordViewModel
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPassword : AppCompatActivity() {
    private lateinit var viewModel : ResetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)


        viewModel = ViewModelProvider(this).get(ResetPasswordViewModel::class.java)
        viewModel.setContext(this)
        change_pass.setOnClickListener {
            val email: String = intent.getStringExtra("email").toString()
            val code : String = intent.getStringExtra("code").toString()
            val pass : String = new_pass.text.toString()
            viewModel.resetPassword(email, code, pass)
            finish()
        }

        btb_change_cancel.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


    /*

    private fun resetPassword(){

        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()
                .create(APIRequest::class.java)


            val email: String = intent.getStringExtra("email").toString()
            val code : String = intent.getStringExtra("code").toString()
            val pass : String = new_pass.text.toString()
            val request = UserResetPasswordRequest(email, code, pass)

            val retrofitData = retrofitBuilder.resetPassword(request)
            Log.e("verification process", "going...")

            retrofitData.enqueue(object: Callback<UserPasswordResetResponse> {
                override fun onResponse(call: Call<UserPasswordResetResponse>, response: Response<UserPasswordResetResponse>) {
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
                        val intent = Intent(this@ResetPassword, LoginActivity::class.java)
                        //intent.putExtra("email", email)
                        startActivity(intent)
                    }

                }

                override fun onFailure(call: Call<UserPasswordResetResponse>, t: Throwable) {
                    Log.e("verification error: ", t.toString())
                }
            })

        } catch (e: Exception) {
            Log.e("verification step 1: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }

    }

     */


}