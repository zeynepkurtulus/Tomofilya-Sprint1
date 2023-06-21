package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityScrollableGalleryBinding


class ScrollableGalleryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScrollableGalleryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_scrollable_gallery)
        val typeFace : Typeface = Typeface.createFromAsset(assets,"Poppins-Regular.ttf")
        binding.btnStartUsing.typeface = typeFace
        binding.btnRegisterOrLogin.typeface = typeFace
        binding.dot1.setImageResource(R.drawable.circle_selected_background)
        binding.horizontalScrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollX: Int = binding.horizontalScrollView.scrollX
            val imageWidth: Int = binding.imageLayout.getChildAt(0).width
            when (scrollX) {
                in 0 until imageWidth -> {
                    binding.dot1.setImageResource(R.drawable.circle_selected_background)
                    binding.dot2.setImageResource(R.drawable.circle_background)
                    binding.dot3.setImageResource(R.drawable.circle_background)
                }
                in imageWidth until 2 * imageWidth -> {
                    binding.dot1.setImageResource(R.drawable.circle_background)
                    binding.dot2.setImageResource(R.drawable.circle_selected_background)
                    binding.dot3.setImageResource(R.drawable.circle_background)
                }
                else -> {
                    binding.dot1.setImageResource(R.drawable.circle_background)
                    binding.dot2.setImageResource(R.drawable.circle_background)
                    binding.dot3.setImageResource(R.drawable.circle_selected_background)
                }
            }
        }

        binding.btnRegisterOrLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }


}