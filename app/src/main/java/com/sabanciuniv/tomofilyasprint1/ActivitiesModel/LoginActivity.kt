package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.sabanciuniv.tomofilyasprint1.ViewModel.LoginActivityViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel : LoginActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(com.sabanciuniv.tomofilyasprint1.R.layout.activity_login)
        
        val typeFace : Typeface = Typeface.createFromAsset(assets,"Poppins-Regular.ttf")
        forget_pass.typeface = typeFace
        log_in_button.typeface = typeFace
        switch_login.typeface = typeFace
        switch_register.typeface = typeFace
        cont_w_google.typeface = typeFace
        text_or.typeface = typeFace
        login_email.typeface = typeFace
        login_pass.typeface = typeFace
        viewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)
        viewModel.setContext(this)
        switch_login.setOnClickListener{
            startActivity(Intent(this@LoginActivity, WelcomePage::class.java))
            finish()
        }
        log_in_button.setOnClickListener {
            val login_email : String = login_email.text.toString()
            val login_pass : String = login_pass.text.toString()
            viewModel.loginUser(login_email, login_pass)
        }



        cont_w_google.setOnClickListener {

            signIn()
        }

        forget_pass.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
            finish()
        }
    }

    /*

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

     */


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

        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                    navigateToSecondActivity()
                } catch (e: ApiException) {
                    // Handle the exception
                    // You can display an error message or take appropriate action
                    e.printStackTrace()
                }
            } else {
                // Handle the case when the sign-in process was canceled or failed
                // You can display an error message or take appropriate action
                // For example, you might want to call super.onBackPressed() here

            }
        }
    }

    private fun navigateToSecondActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }




}