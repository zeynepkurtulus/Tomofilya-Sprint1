package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.sabanciuniv.tomofilyasprint1.viewModel.LoginActivityViewModel
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityLoginBinding



class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel : LoginActivityViewModel
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,com.sabanciuniv.tomofilyasprint1.R.layout.activity_login)
        
        val typeFace : Typeface = Typeface.createFromAsset(assets,"Poppins-Regular.ttf")
        binding.forgetPass.typeface = typeFace
        binding.logInButton.typeface = typeFace
        binding.switchLogin.typeface = typeFace
        binding.switchRegister.typeface = typeFace
        binding.contWGoogle.typeface = typeFace
        binding.textOr.typeface = typeFace
        binding.loginEmail.typeface = typeFace
        binding.loginPass.typeface = typeFace
        viewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)
        viewModel.setContext(this)
        binding.switchLogin.setOnClickListener{
            startActivity(Intent(this@LoginActivity, WelcomePage::class.java))
            finish()
        }


        binding.logInButton.setOnClickListener {
            val login_email : String = binding.loginEmail.text.toString()
            val login_pass : String = binding.loginPass.text.toString()
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