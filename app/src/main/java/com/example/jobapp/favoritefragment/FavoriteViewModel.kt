package com.example.jobapp.favoritefragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.jobapp.model.JobModel

import com.example.jobapp.roomdatabase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class FavoriteViewModel : ViewModel(){

    val jobResultLiveData = MutableLiveData<List<JobModel>>()

    fun showDataFromDatabase(context: Context){
        CoroutineScope(Dispatchers.IO).async {

            val dataBase : AppDataBase = Room.databaseBuilder(context , AppDataBase::class.java , "job").build()

            CoroutineScope(Dispatchers.Main).async {

                jobResultLiveData.value = dataBase.jobDao().selectAllJob()

            }
        }
    }
}