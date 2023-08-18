package com.sabanciuniv.tomofilyasprint1.network
import android.Manifest
import com.sabanciuniv.tomofilyasprint1.R

object Constants {
    const val baseURL = "https://tomofilya.azurewebsites.net/"
    const val ApiKey = "COF40RZ95GBJZ7R08QVJMIDR0TLEJL1DDEXY10K0H8MQ03DJJ8"
    const val api = "apikey: COF40RZ95GBJZ7R08QVJMIDR0TLEJL1DDEXY10K0H8MQ03DJJ8"
    const val contentType = "Content-Type: application/json"
    const val multiContentType = "Content-Type: multipart/form-data"


    var TOKEN = "null"
    var c1 = false
    var c2 = false
    var c3 = false
    var c4 = false
    var imageCount = 0

    var path1: String? = null
    var path2: String? = null
    var path3: String? = null
    var path4: String? = null

    const val REQUEST_IMAGE_CAPTURE = 1
    const val SELECT_PHOTO = 2

}