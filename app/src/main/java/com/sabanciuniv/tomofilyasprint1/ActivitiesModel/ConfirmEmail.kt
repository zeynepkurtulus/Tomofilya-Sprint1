package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.viewModel.ConfirmEmailViewModel
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityConfirmEmailBinding


class ConfirmEmail : AppCompatActivity() {
    private lateinit var binding : ActivityConfirmEmailBinding
    private lateinit var viewModel : ConfirmEmailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ConfirmEmailViewModel::class.java)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_email)
        binding.btnSendCode.setOnClickListener {
            val d1 : String = binding.digit1.text.toString()
            val d2 : String = binding.digit2.text.toString()
            val d3 : String = binding.digit3.text.toString()
            val d4 : String = binding.digit4.text.toString()
            val email: String = intent.getStringExtra(R.string.emailStr.toString()).toString()
            viewModel.emailConfirm(d1,d2,d3,d4,email)
            //emailConfirm()
        }
        binding.originalResendPass.setOnClickListener {
            val email: String = intent.getStringExtra(R.string.emailStr.toString()).toString()
            viewModel.forgotPassSendVerifyCode(email)
            //forgotPassSendVerifyCode(email)
        }


    }



}