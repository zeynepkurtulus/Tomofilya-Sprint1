package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val typeFace : Typeface = Typeface.createFromAsset(assets,"Poppins-Regular.ttf")
        binding.splashWelcome.typeface = typeFace
        Handler().postDelayed({
            startActivity(Intent(this, ScrollableGalleryActivity::class.java))
            finish()
        },2000

        )

    }

}