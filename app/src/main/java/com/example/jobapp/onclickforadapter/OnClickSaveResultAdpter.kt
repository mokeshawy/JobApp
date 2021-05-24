package com.example.jobapp.onclickforadapter

import com.example.jobapp.adapter.RecyclerSaveResultAdapter
import com.example.jobapp.model.FavoriteJobModel
import com.example.jobapp.model.JobModel

interface OnClickSaveResultAdpter {

    fun onClickSaveResult( viewHolder: RecyclerSaveResultAdapter.ViewHolder,
                           jobModel: JobModel,
                           position: Int)
}