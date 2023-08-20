package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.viewModel.LoginActivityViewModel
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityLoginBinding
import com.sabanciuniv.tomofilyasprint1.model.AuthenticationSocial.AuthenticationLoginDataResponse
import com.sabanciuniv.tomofilyasprint1.network.APIRequest
import com.sabanciuniv.tomofilyasprint1.network.AuthenticationLoginRequest
import com.sabanciuniv.tomofilyasprint1.network.Constants
import com.sabanciuniv.tomofilyasprint1.network.SharedDataRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel : LoginActivityViewModel
    private lateinit var binding : ActivityLoginBinding
    val loading = ProgressBarDialog(this)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,com.sabanciuniv.tomofilyasprint1.R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)
        viewModel.setContext(this)
        val sharedDataRepository = SharedDataRepository.getInstance()
        binding.switchLogin.setOnClickListener{
            startActivity(Intent(this@LoginActivity, WelcomePage::class.java))
            finish()
        }

        binding.logInButton.setOnClickListener {
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object :Runnable{
                override fun run(){
                    loading.isDismiss()
                }
            }, 5000)
            val login_email: String = binding.loginEmail.text.toString()
            sharedDataRepository.email = login_email
            val login_pass: String = binding.loginPass.text.toString()
            sharedDataRepository.password = login_pass
            Log.e("Email", login_email)
            Log.e("Password", login_pass)
            viewModel.loginUser(login_email, login_pass)
        }


        binding.contWGoogle.setOnClickListener {

            signIn()
        }

        binding.forgetPass.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
            finish()
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