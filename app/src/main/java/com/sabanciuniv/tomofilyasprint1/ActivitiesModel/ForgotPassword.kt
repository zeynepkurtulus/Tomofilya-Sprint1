package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.viewModel.ForgotPasswordViewModel
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityForgotPasswordBinding


class ForgotPassword : AppCompatActivity() {
    private lateinit var binding : ActivityForgotPasswordBinding
    private lateinit var viewModel : ForgotPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_password)
        val typeFace : Typeface = Typeface.createFromAsset(assets,"Poppins-Regular.ttf")
        binding.forgotPassword.typeface = typeFace
        binding.email.typeface = typeFace
        binding.emailDesc.typeface = typeFace
        binding.btnCancel.typeface = typeFace
        binding.btnSend.typeface = typeFace


        binding.btnSend.setOnClickListener {
            viewModel.forgotPassSendVerifyCode(binding.forgotPassEmailField.text.toString())
            //forgotPassSendVerifyCode(forgot_pass_email_field.text.toString())
        }



        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}