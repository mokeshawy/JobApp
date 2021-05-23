package com.example.jobapp.homescreenfragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.jobapp.retrofit.RetrofitBuilder
import com.example.jobapp.response.JobsResponse
import com.example.jobapp.roomdatabase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class HomeScreenViewModel : ViewModel() {


    val jobResultLiveData = MutableLiveData<List<JobsResponse>>()


    // get data from api and show in home screen page.
    fun getJobResult(){

        CoroutineScope(Dispatchers.IO).async {

            val response = RetrofitBuilder.getJobApi().getJobList("api")

            CoroutineScope(Dispatchers.Main).async {

                jobResultLiveData.value = response.body()

            }
        }
    }
}