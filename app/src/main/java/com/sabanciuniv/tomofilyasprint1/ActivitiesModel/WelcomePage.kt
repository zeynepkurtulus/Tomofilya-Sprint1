package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan


import android.widget.TextView
import com.sabanciuniv.tomofilyasprint1.viewModel.WelcomePageViewModel
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

import com.sabanciuniv.tomofilyasprint1.R


import com.sabanciuniv.tomofilyasprint1.databinding.ActivityWelcomePageBinding

class WelcomePage : AppCompatActivity() {
    private lateinit var binding : ActivityWelcomePageBinding
    private lateinit var viewModel : WelcomePageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_page)
        viewModel = ViewModelProvider(this).get(WelcomePageViewModel::class.java)
        viewModel.setContext(this)
        binding.welcomePageViewModel = viewModel
        val typeFace : Typeface =Typeface.createFromAsset(assets,"Poppins-Regular.ttf")
        binding.userContract.typeface = typeFace
        binding.accept.typeface = typeFace
        binding.switchLogin.typeface = typeFace
        binding.switchRegister.typeface = typeFace
        binding.passfield.typeface = typeFace
        binding.emailTxt.typeface = typeFace
        binding.nameSurnameTxt.typeface = typeFace
        binding.register.typeface = typeFace
        binding.textOr.typeface = typeFace
        setColor(binding.userContract)
        binding.contWGoogle.typeface = typeFace




        binding.switchRegister.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        //MOVED THIS PART INTO XML FILE
        /*
        binding.register.setOnClickListener {
            if (viewModel.checkBoxChecker(binding.checkBox)){
                //sendVerificationCode()
                Log.e("onclick girdi" ,"success")
                Log.e(binding.nameSurnameTxt.text.toString(), "name")
                Log.e(binding.emailTxt.text.toString(), "email")
                Log.e(binding.passwordTxt.text.toString(), "password")
                binding.nameSurnameTxt.text.toString()
                viewModel.sendVerificationCode(binding.nameSurnameTxt.text.toString(),binding.emailTxt.text.toString(), binding.passwordTxt.text.toString())
                //sendVerificationCode()
                viewModel.is_verif().observe(this, Observer {

                })

            }
            else{
                Toast.makeText(this, "Lütfen Hüküm ve Koşulları Kabul Ediniz", Toast.LENGTH_LONG).show()
            }

        }

         */

        binding.contWGoogle.setOnClickListener {
            signIn()
        }


    }

    private fun setColor(contract : TextView){
        val redWords : List<String> = listOf("Üyelik Sözleşmesi", "Kişisel Veriler ile İlgili Aydınlatma Metni")
        val text = contract.text.toString()
        val spannableString = SpannableString(text)

        for (word in redWords) {
            val start = text.indexOf(word)
            val end = start + word.length
            spannableString.setSpan(
                ForegroundColorSpan(Color.RED),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        contract.text = spannableString

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

            }
        }
    }



    private fun navigateToSecondActivity() {
        val intent = Intent(this@WelcomePage, MainActivity::class.java)
        startActivity(intent)
    }




}