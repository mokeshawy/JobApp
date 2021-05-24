package com.example.jobapp.onclickforadapter

import com.example.jobapp.adapter.SearchAdapter
import com.example.jobapp.model.JobModel

interface OnClickSearchAdapter {

    fun onClickSearchResult(viewHolder: SearchAdapter.ViewHolder,
                          jobModel: JobModel,
                          position: Int)
}