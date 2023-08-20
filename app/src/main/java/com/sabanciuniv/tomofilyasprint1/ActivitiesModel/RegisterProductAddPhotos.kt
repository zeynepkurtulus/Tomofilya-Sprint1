package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityHomePageCategoryForRequestBinding
import com.sabanciuniv.tomofilyasprint1.databinding.ActivityRegisterProductAddPhotosBinding
import com.sabanciuniv.tomofilyasprint1.network.Constants
import com.sabanciuniv.tomofilyasprint1.network.Constants.REQUEST_IMAGE_CAPTURE
import com.sabanciuniv.tomofilyasprint1.network.Constants.SELECT_PHOTO
import com.sabanciuniv.tomofilyasprint1.network.SharedDataRepository
import com.sabanciuniv.tomofilyasprint1.viewModel.LoginCallBack
import com.sabanciuniv.tomofilyasprint1.viewModel.PathFinder
import com.sabanciuniv.tomofilyasprint1.viewModel.RegisterProductAddPhotosViewModel



class RegisterProductAddPhotos : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterProductAddPhotosBinding
    private lateinit var viewModel: RegisterProductAddPhotosViewModel
    private var productId: Int? = null
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(RegisterProductAddPhotosViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_product_add_photos)
        viewModel.setContext(this)
        super.onCreate(savedInstanceState)

        binding.registerProductAddPhotosViewModel = viewModel
        productId = intent.getStringExtra("productId")?.toIntOrNull()
        val sharedDataRepository = SharedDataRepository.getInstance()
        email = sharedDataRepository.email
        password = sharedDataRepository.password
        Log.e("tag", "Email is: $email")
        Log.e("tag", "Password is: $password")

        viewModel.resetCams()
        //checks the permission
        binding.btnTakePhoto.setOnClickListener {
            viewModel.checkPermission()
        }

        binding.backBtn.setOnClickListener {
            super.onBackPressed()
        }

        //opens up the camera if the permission is granted
        viewModel.permissionGranted.observe(this) { isPermissionGranted ->
            if (isPermissionGranted) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
            } else {
                // Handle the case when the permission is not granted
                // For example, you can show a message or request the permission again.
            }
        }

        //opens up the gallery inside the phone
        binding.uploadPhotosGallery.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, SELECT_PHOTO)
        }


        binding.saveButtonPhoto.setOnClickListener {
            Log.e("prodID", productId.toString())
            viewModel.uploadImageToServer(productId!!,Constants.TOKEN)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try{
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
                val photo: Bitmap? = data?.extras?.get("data") as Bitmap?
                val uri = viewModel.getImageUri(this, photo!!)
                Log.e("true uri", uri.toString())
                val path = PathFinder.getRealPath(this, uri!!)
                viewModel.placePath(path)
                Log.e("path", path.toString())
                viewModel.loadImage(uri)
            }
            else if (requestCode == SELECT_PHOTO) {
                if (resultCode == RESULT_OK) {
                    if (intent != null) {
                        val clipData = data?.clipData
                        if (clipData != null && clipData.itemCount > 0) {
                            // If multiple images were selected, process each one
                            for (i in 0 until clipData.itemCount) {

                                val uri = clipData.getItemAt(i).uri
                                Log.e("tag", "Uri inside activity: $uri")
                                viewModel.loadImage(uri)
                            }
                        }
                    }
                }
                super.onActivityResult(requestCode, resultCode, intent);
            }
        }
        catch (e: Exception){
            Log.e("exception", e.toString())
        }
        fun deleteFromActivity(v: View){
            viewModel.discardPhoto(v)
        }
        fun deletePhoto(v: View){
            viewModel.deletePhoto(v)
        }

        binding.addPhoto1.setOnClickListener {
            deleteFromActivity(it)
        }
        binding.photo2.setOnClickListener {
            deleteFromActivity(it)
        }

        binding.photo3.setOnClickListener {
            deleteFromActivity(it)
        }
        binding.photo4.setOnClickListener {
            deleteFromActivity(it)
        }

        binding.btnCancel1.setOnClickListener {
            deletePhoto(it)
        }





    }

}