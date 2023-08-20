package com.sabanciuniv.tomofilyasprint1.viewModel

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import android.Manifest
import android.content.ContentResolver
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.sabanciuniv.tomofilyasprint1.model.AuthenticationSocial.AuthenticationLoginDataResponse
import com.sabanciuniv.tomofilyasprint1.model.ProductPhotosPost.ProductPhotosPostResponse
import com.sabanciuniv.tomofilyasprint1.network.APIRequest
import com.sabanciuniv.tomofilyasprint1.network.AuthenticationLoginRequest
import com.sabanciuniv.tomofilyasprint1.network.Constants
import com.sabanciuniv.tomofilyasprint1.network.Constants.c1
import com.sabanciuniv.tomofilyasprint1.network.Constants.c2
import com.sabanciuniv.tomofilyasprint1.network.Constants.c3
import com.sabanciuniv.tomofilyasprint1.network.Constants.c4
import com.sabanciuniv.tomofilyasprint1.network.Constants.imageCount
import com.sabanciuniv.tomofilyasprint1.network.Constants.path1
import com.sabanciuniv.tomofilyasprint1.network.Constants.path2
import com.sabanciuniv.tomofilyasprint1.network.Constants.path3
import com.sabanciuniv.tomofilyasprint1.network.Constants.path4
import com.squareup.picasso.Picasso
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.util.*

class RegisterProductAddPhotosViewModel : ViewModel() {
    private lateinit var ctx: Context
    private var isCameraPermissionGranted: Boolean = false
    private var cam1: ImageView? = null
    private var cam2: ImageView? = null
    private var cam3: ImageView? = null
    private var cam4: ImageView? = null

    private lateinit var photo1Uri: Uri
    private lateinit var photo2Uri: Uri
    private lateinit var photo3Uri: Uri
    private lateinit var photo4Uri: Uri


    private val _permissionGranted = MutableLiveData<Boolean>()
    val permissionGranted: LiveData<Boolean>
        get() = _permissionGranted




    private lateinit var retrofitClient: RetrofitClient
    fun setContext(ctx: Context) {
        this.ctx = ctx
        retrofitClient = RetrofitClient(ctx)
        cam1 = (ctx as? Activity)?.findViewById(R.id.addPhoto1)
        Log.e("tag", "cam1: $cam1")
        cam2 = (ctx as? Activity)?.findViewById(R.id.photo2)
        cam3 = (ctx as? Activity)?.findViewById(R.id.photo3)
        cam4 = (ctx as? Activity)?.findViewById(R.id.photo4)
    }


    fun checkPermission() {
        Dexter.withContext(ctx)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    isCameraPermissionGranted = true
                    Log.e("tag", "Permissions Granted")
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    isCameraPermissionGranted = false
                    Log.e("tag", "Permission denied")
                    /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: com.karumi.dexter.listener.PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()/* ... */
                }
            }).check()
        _permissionGranted.value = isCameraPermissionGranted
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun placePath(path: String?) {
        if(path1 == null) path1 = path
        else if(path2 == null) path2 = path
        else if(path3 == null) path3 = path
        else if(path4 == null) path4 = path
    }

    fun loadImage(uri: Uri?) {
        imageCount++
        if (!c1) {
            Log.e("tag", "c1 is: $c1")

            val message = Picasso.with(ctx).load(uri).fit().into(cam1)
            Log.e("tag", "picasso: $message")
            photo1Uri = uri!!
            Log.e("tag", "photo1Uri is: $photo1Uri")
            c1 = true
        } else if (!c2) {
            Log.e("tag", "c2 is: $c2")
            Picasso.with(ctx).load(uri).fit().into(cam2)
            photo2Uri = uri!!
            c2 = true
        } else if (!c3) {
            Log.e("tag", "c3 is: $c3")
            Picasso.with(ctx).load(uri).fit().into(cam3)
            photo3Uri = uri!!
            c3 = true
        } else if (!c4) {
            Log.e("tag", "c4 is: $c4")
            Picasso.with(ctx).load(uri).fit().into(cam4)
            photo4Uri = uri!!
            c4 = true
        } else {
            Toast.makeText(ctx, ctx.getString(R.string.exceededPhotoLimit), Toast.LENGTH_SHORT).show()
        }
    }

    fun resetCams() {
        c1 = false
        c2 = false
        c3 = false
        c4 = false
    }

    fun discardPhoto(v: View) {
        val cancelButton1 = (ctx as? Activity)?.findViewById<Button>(R.id.btn_cancel1)
        val cancelButton2 = (ctx as? Activity)?.findViewById<Button>(R.id.btn_cancel2)
        val cancelButton3 = (ctx as? Activity)?.findViewById<Button>(R.id.btn_cancel3)
        val cancelButton4 = (ctx as? Activity)?.findViewById<Button>(R.id.btn_cancel4)

        when (v.id) {
            R.id.addPhoto1 -> {
                cancelButton1?.visibility = View.VISIBLE
                cancelButton2?.visibility = View.GONE
                cancelButton3?.visibility = View.GONE
                cancelButton4?.visibility = View.GONE
            }
            R.id.photo2 -> {
                cancelButton1?.visibility = View.GONE
                cancelButton2?.visibility = View.VISIBLE
                cancelButton3?.visibility = View.GONE
                cancelButton4?.visibility = View.GONE
            }
            R.id.photo3 -> {
                cancelButton1?.visibility = View.GONE
                cancelButton2?.visibility = View.GONE
                cancelButton3?.visibility = View.VISIBLE
                cancelButton4?.visibility = View.GONE
            }
            R.id.photo4 -> {
                cancelButton1?.visibility = View.GONE
                cancelButton2?.visibility = View.GONE
                cancelButton3?.visibility = View.GONE
                cancelButton4?.visibility = View.VISIBLE
            }
        }
    }

    fun deletePhoto(v: View) {
        when (v.id) {
            R.id.btn_cancel1 -> {
                changePhotos(1)
            }
            R.id.btn_cancel2 -> {
                changePhotos(2)
            }
            R.id.btn_cancel3 -> {
                changePhotos(3)
            }
            R.id.btn_cancel4 -> {
                changePhotos(4)
            }
        }
    }

    private fun changePhotos(i: Int) {
        if (imageCount == 0) return
        val photo1 = (ctx as? Activity)?.findViewById<ImageView>(R.id.addPhoto1)
        val photo2 = (ctx as? Activity)?.findViewById<ImageView>(R.id.photo2)
        val photo3 = (ctx as? Activity)?.findViewById<ImageView>(R.id.photo3)
        val photo4 = (ctx as? Activity)?.findViewById<ImageView>(R.id.photo4)

        val imageList = listOf<ImageView?>(photo1, photo2, photo3, photo4)
        imageCount--
        changeCList(imageCount)
        for (k in i..4) {
            if (k == i) {
                //delete k
                val toBeDeleted = imageList[k - 1]
                toBeDeleted?.setImageResource(0)
            } else {
                //k-1 = k
                moveAlongPhotos(k - 1)
            }
        }
    }

    private fun moveAlongPhotos(k: Int) {
        val photo1 = (ctx as? Activity)?.findViewById<ImageView>(R.id.addPhoto1)
        val photo2 = (ctx as? Activity)?.findViewById<ImageView>(R.id.photo2)
        val photo3 = (ctx as? Activity)?.findViewById<ImageView>(R.id.photo3)
        val photo4 = (ctx as? Activity)?.findViewById<ImageView>(R.id.photo4)

        when (k) {

            1 -> {
                photo1?.setImageResource(0)
                photo1?.setImageDrawable(photo2?.drawable)
                photo2?.setImageResource(0)
            }
            2 -> {
                photo2?.setImageResource(0)
                photo2?.setImageDrawable(photo3?.drawable)
                photo3?.setImageResource(0)
            }
            3 -> {
                photo3?.setImageResource(0)
                photo3?.setImageDrawable(photo4?.drawable)
                photo4?.setImageResource(0)
            }
            4 -> {
                photo4?.setImageResource(0)
            }
        }
    }

    private fun changeCList(k: Int) {
        when (k) {
            0 -> {
                c1 = false
                c2 = false
                c3 = false
                c4 = false
                path1 = "null"
            }
            1 -> {
                c1 = true
                c2 = false
                c3 = false
                c4 = false
                path2 = "null"
            }
            2 -> {
                c1 = true
                c2 = true
                c3 = false
                c4 = false
                path3 = "null"
            }
            3 -> {
                c1 = true
                c2 = true
                c3 = true
                c4 = false
                path4 = "null"
            }
            4 -> {
                c1 = true
                c2 = true
                c3 = true
                c4 = true
            }
        }
    }

    fun uploadImageToServer(productId: Int, token :String) {
        val retrofitBuilder = retrofitClient.createAPIRequestWithToken(token)
        val contentResolver = ctx.contentResolver
        val imageFile = getImageFileFromUri(contentResolver, photo1Uri)
        val compressedBitmap = BitmapFactory.decodeFile(imageFile?.path)
        val compressedImageStream = ByteArrayOutputStream()
        compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, compressedImageStream)
        val compressedImageByteArray = compressedImageStream.toByteArray()
        val imageRequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), compressedImageByteArray)
        val imagePart = MultipartBody.Part.createFormData("Product", imageFile?.name, imageRequestBody)

        val productIdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), productId.toString())


        val request = retrofitBuilder.addPhotosWithBoundary(token, imagePart, productIdRequestBody)
        request.enqueue(object :Callback<ProductPhotosPostResponse> {
            override fun onResponse(
                call: Call<ProductPhotosPostResponse>,
                response: retrofit2.Response<ProductPhotosPostResponse>
            ) {
                // Handle the response from the server
                if (response.isSuccessful) {
                    // Request successful, handle response data
                    val responseData = response.body()
                    Log.e("response","response: $response.toString()")
                    Log.e("response body", " response body: $response.body().toString()")
                    Log.e("response success", " success message: $response.body()?.success.toString()")
                    // ...
                } else {
                    Log.e("response","response: $response.toString()")
                    Log.e("response body", " response body: $response.body().toString()")
                    Log.e("response success", " success message: $response.body()?.success.toString()")
                }
            }

            override fun onFailure(call: Call<ProductPhotosPostResponse>, t: Throwable) {
                // Handle network or other errors
                // ...
            }
        })
    }


    private fun getImageFileFromUri(contentResolver: ContentResolver, uri: Uri): File? {
        var inputStream: InputStream? = null
        var file: File? = null
        try {
            inputStream = contentResolver.openInputStream(uri)
            val fileName = getFileName(contentResolver, uri)
            val fileExtension = getFileExtension(contentResolver, uri)
            file = createTemporaryFile(fileName, fileExtension)
            if (file != null) {
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                outputStream.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }
        return file
    }

    private fun getFileName(contentResolver: ContentResolver, uri: Uri): String {
        var name = ""
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    name = it.getString(nameIndex)
                }
            }
        }
        return name
    }

    private fun getFileExtension(contentResolver: ContentResolver, uri: Uri): String {
        var extension = ""
        val mime = contentResolver.getType(uri)
        if (mime != null) {
            val parts = mime.split("/")
            if (parts.size == 2) {
                extension = parts[1]
            }
        }
        return extension
    }

    private fun createTemporaryFile(fileName: String, fileExtension: String): File? {
        val tempDir = File(System.getProperty("java.io.tmpdir"))
        try {
            return File.createTempFile(fileName, ".$fileExtension", tempDir)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}