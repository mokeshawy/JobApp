package com.example.jobapp.retrofit

import com.example.jobapp.util.Constants
import com.example.jobsapp.response.JobsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ConnectionEndPoint {

    @GET(Constants.END_POINT)
    suspend fun getJobList(@Query("description") description : String) : Response<List<JobsResponse>>
}