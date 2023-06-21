package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.viewModel.ConfirmEmailWhenForgotPasswordViewModel
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityConfirmEmailWhenForgotPasswordBinding


class ConfirmEmailWhenForgotPassword : AppCompatActivity() {
    private lateinit var binding : ActivityConfirmEmailWhenForgotPasswordBinding
    private lateinit var viewModel : ConfirmEmailWhenForgotPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ConfirmEmailWhenForgotPasswordViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm_email_when_forgot_password)

        binding.forgotPassBtnSendCode.setOnClickListener {
            val d1 : String = binding.forgotPassDigit1.text.toString()
            val d2 : String = binding.forgotPassDigit2.text.toString()
            val d3 : String = binding.forgotPassDigit3.text.toString()
            val d4 : String = binding.forgotPassDigit4.text.toString()
            val email: String = intent.getStringExtra(R.string.emailStr.toString()).toString()
            viewModel.emailConfirm(d1,d2,d3,d4,email)
            //emailConfirm()

        }

        binding.sendPassAgain.setOnClickListener {
            val email: String = intent.getStringExtra(R.string.emailStr.toString()).toString()
            viewModel.forgotPassSendVerifyCode(email)
        }
    }


}