package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.sabanciuniv.tomofilyasprint1.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val typeFace : Typeface = Typeface.createFromAsset(assets,"Poppins-Regular.ttf")
        splash_welcome.typeface = typeFace
        Handler().postDelayed({
            startActivity(Intent(this, ScrollableGalleryActivity::class.java))
            finish()
        },2000

        )

    }

}