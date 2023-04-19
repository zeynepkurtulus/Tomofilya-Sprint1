package com.sabanciuniv.tomofilyasprint1.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.api.APIRequest
import com.sabanciuniv.tomofilyasprint1.api.AuthenticationLoginRequest
import com.sabanciuniv.tomofilyasprint1.api.AuthenticationLoginWithGoogleAppleRequest
import com.sabanciuniv.tomofilyasprint1.api.Constants
import com.sabanciuniv.tomofilyasprint1.data.AuthenticationSocial.AuthenticationLoginDataResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sabanciuniv.tomofilyasprint1.R.layout.activity_login)
        
        val typeFace : Typeface = Typeface.createFromAsset(assets,"Poppins-Regular.ttf")
        forget_pass.typeface = typeFace
        log_in_button.typeface = typeFace
        switch_login.typeface = typeFace
        switch_register.typeface = typeFace
        cont_w_google.typeface = typeFace
        cont_w_apple.typeface = typeFace
        text_or.typeface = typeFace
        login_email.typeface = typeFace
        login_pass.typeface = typeFace

        switch_login.setOnClickListener {
            startActivity(Intent(this, WelcomePage::class.java))
            finish()
        }
        log_in_button.setOnClickListener {
            loginUser()
        }



        cont_w_google.setOnClickListener {

            loginUser()
        }

        forget_pass.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
            finish()
        }
    }

    private fun loginUser(){

        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()
                .create(APIRequest::class.java)

                val login_email : String = login_email.text.toString()
                val login_pass : String = login_pass.text.toString()


            val request = AuthenticationLoginRequest(login_email, login_pass)

            val retrofitData = retrofitBuilder.login(request)
            Log.e("verification process", "going...")

            retrofitData.enqueue(object: Callback<AuthenticationLoginDataResponse> {
                override fun onResponse(call: Call<AuthenticationLoginDataResponse>, response: Response<AuthenticationLoginDataResponse>) {
                    Log.e("verification response:", "retrieving body")
                    Log.e("verification r body", response.raw().toString())
                    val responseBody = response.body()
                    Log.e("verification success", responseBody?.success.toString())
                    Log.e("verification message", responseBody?.message.toString())


                    if (responseBody?.success == true){
                        val intent = Intent(this@LoginActivity, WelcomePage::class.java)
                        startActivity(intent)
                        finish()
                    }

                }

                override fun onFailure(call: Call<AuthenticationLoginDataResponse>, t: Throwable) {
                    Log.e("verification error: ", t.toString())
                }
            })

        } catch (e: Exception) {
            Log.e("verification step 1: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }

    }


    private fun signIn() {
        val gso : GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val gsc : GoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent : Intent = gsc.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1000) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            task.getResult(ApiException::class.java)
            navigateToSecondActivity()
        }
    }

    private fun navigateToSecondActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }




}