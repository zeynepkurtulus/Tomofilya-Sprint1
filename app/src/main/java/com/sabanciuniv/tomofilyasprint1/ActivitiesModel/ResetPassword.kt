package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.viewModel.ResetPasswordViewModel
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityResetPasswordBinding


class ResetPassword : AppCompatActivity() {
    private lateinit var binding : ActivityResetPasswordBinding
    private lateinit var viewModel : ResetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_reset_password)
        //setContentView(R.layout.activity_reset_password)
        viewModel = ViewModelProvider(this).get(ResetPasswordViewModel::class.java)
        viewModel.setContext(this)
        binding.changePass.setOnClickListener {
            val email: String = intent.getStringExtra(R.string.emailStr.toString()).toString()
            val code : String = intent.getStringExtra(R.string.code.toString()).toString()
            val pass : String = binding.newPass.text.toString()
            viewModel.resetPassword(email, code, pass)
            finish()
        }

        binding.btbChangeCancel.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


}