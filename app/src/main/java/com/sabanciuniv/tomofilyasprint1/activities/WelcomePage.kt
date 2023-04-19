package com.sabanciuniv.tomofilyasprint1.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.CheckBox

import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.api.APIRequest
import com.sabanciuniv.tomofilyasprint1.api.Constants
import com.sabanciuniv.tomofilyasprint1.api.UserPostRequest
import com.sabanciuniv.tomofilyasprint1.data.UserPost.UserPostResponse
import kotlinx.android.synthetic.main.activity_welcome_page.*
import kotlinx.android.synthetic.main.activity_welcome_page.cont_w_apple
import kotlinx.android.synthetic.main.activity_welcome_page.cont_w_google
import kotlinx.android.synthetic.main.activity_welcome_page.switch_login
import kotlinx.android.synthetic.main.activity_welcome_page.switch_register
import kotlinx.android.synthetic.main.activity_welcome_page.text_or
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WelcomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)
        val typeFace : Typeface =Typeface.createFromAsset(assets,"Poppins-Regular.ttf")
        user_contract.typeface = typeFace
        accept.typeface = typeFace
        switch_login.typeface = typeFace
        switch_register.typeface = typeFace
        passfield.typeface = typeFace
        emailfield.typeface = typeFace
        namefield.typeface = typeFace
        register.typeface = typeFace
        text_or.typeface = typeFace
        setColor(user_contract)
        cont_w_google.typeface = typeFace
        cont_w_apple.typeface = typeFace




        switch_register.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        register.setOnClickListener {
            if (checkBoxChecker(checkBox)){
                sendVerificationCode()
            }
            else{
                Toast.makeText(this, "Lütfen Hüküm ve Koşulları Kabul Ediniz", Toast.LENGTH_LONG).show()
            }

        }




    }

    public fun setColor(contract : TextView){
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

    private fun checkBoxChecker(checkBox: CheckBox): Boolean{
        return checkBox.isChecked
    }

    private fun sendVerificationCode(){


        try {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build()
                .create(APIRequest::class.java)

            val name: String = name_surname_txt.text.toString()
            val email: String = email_txt.text.toString()
            val password: String = password_txt.text.toString()
            val isOpenNotification: Boolean = true // set to true for now

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
                        val intent = Intent(this@WelcomePage, ConfirmEmail::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
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


    fun showErrorSnackBar(message : String){
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view // need this to set an individual background color
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_error_color))
        snackBar.show()
    }
    private fun validateForm(name: TextInputEditText, email: TextInputEditText, password: TextInputEditText): Boolean{

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


}