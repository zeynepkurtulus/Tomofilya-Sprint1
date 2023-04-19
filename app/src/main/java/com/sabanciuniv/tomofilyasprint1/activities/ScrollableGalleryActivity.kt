package com.sabanciuniv.tomofilyasprint1.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sabanciuniv.tomofilyasprint1.R
import kotlinx.android.synthetic.main.activity_scrollable_gallery.*

class ScrollableGalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrollable_gallery)
        val typeFace : Typeface = Typeface.createFromAsset(assets,"Poppins-Regular.ttf")
        btn_start_using.typeface = typeFace
        btn_register_or_login.typeface = typeFace
        dot1.setImageResource(R.drawable.circle_selected_background)
        horizontalScrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollX: Int = horizontalScrollView.scrollX
            val imageWidth: Int = imageLayout.getChildAt(0).width
            when (scrollX) {
                in 0 until imageWidth -> {
                    dot1.setImageResource(R.drawable.circle_selected_background)
                    dot2.setImageResource(R.drawable.circle_background)
                    dot3.setImageResource(R.drawable.circle_background)
                }
                in imageWidth until 2 * imageWidth -> {
                    dot1.setImageResource(R.drawable.circle_background)
                    dot2.setImageResource(R.drawable.circle_selected_background)
                    dot3.setImageResource(R.drawable.circle_background)
                }
                else -> {
                    dot1.setImageResource(R.drawable.circle_background)
                    dot2.setImageResource(R.drawable.circle_background)
                    dot3.setImageResource(R.drawable.circle_selected_background)
                }
            }
        }

        btn_register_or_login.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }


}