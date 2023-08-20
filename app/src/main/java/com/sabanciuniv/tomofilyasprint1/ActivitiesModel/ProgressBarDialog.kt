package com.sabanciuniv.tomofilyasprint1.ActivitiesModel

import android.app.Activity
import android.app.AlertDialog
import com.sabanciuniv.tomofilyasprint1.R

class ProgressBarDialog(val mActivity: Activity) {
    private lateinit var isDialog : AlertDialog
    fun startLoading(){
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.progress_bar, null)
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun isDismiss(){
        isDialog.dismiss()
    }

}