package com.example.jobapp.util

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.example.jobapp.R

object Constants {

    // Retrofit Builder
    const val BASE_URL = "https://jobs.github.com/"
    const val END_POINT = "positions.json"
    const val QUERY_DESCRIPTION = "description"
    const val API_KEY   = "api"

    // Room Database table name
    const val DATA_BASE_NAME = "job"



    // Progress dialog.
    lateinit var mProgressDialog : Dialog
    fun showProgressDialog( text : String ,context: Context){

        mProgressDialog = Dialog(context)

        /* Set the screen content from a layout resource
        The resource will be inflated , adding all top-level views to the screen */
        mProgressDialog.setContentView(R.layout.dialog_progress)
        var dialog = mProgressDialog.findViewById(R.id.tv_progress_textId) as TextView
        dialog.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        mProgressDialog.show()

    }

    // hide progress bar.
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

}