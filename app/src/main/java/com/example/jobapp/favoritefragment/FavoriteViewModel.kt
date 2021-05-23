package com.example.jobapp.favoritefragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.jobapp.model.FavoriteJobModel
import com.example.jobapp.model.JobModel

import com.example.jobapp.roomdatabase.AppDataBase
import com.example.jobapp.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class FavoriteViewModel : ViewModel(){


    val jobSaveResultLiveData = MutableLiveData<List<FavoriteJobModel>>()
    fun showDataFromDatabase(context: Context){
        CoroutineScope(Dispatchers.IO).async {
            val dataBase : AppDataBase = Room.databaseBuilder(context , AppDataBase::class.java , Constants.DATA_BASE_NAME).build()
            CoroutineScope(Dispatchers.Main).async {
                jobSaveResultLiveData.value = dataBase.jobDao().selectAllFromFavorite()

            }
        }
    }
}