package com.example.jobapp.onclickforadapter

import com.example.jobapp.adapter.RecyclerJobsListAdapter
import com.example.jobapp.response.JobsResponse

interface OnClickHomeAdapter {

    fun onclickHomePage( viewHolder: RecyclerJobsListAdapter.ViewHolder , mJobsResponse: List<JobsResponse> , jobsResponse: JobsResponse , position : Int )
}