package com.example.jobapp.favoritefragment

import android.content.Context
import android.widget.ToggleButton
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
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel(){


    val jobSaveResultLiveData = MutableLiveData<List<FavoriteJobModel>>()
    // show result data from database for add to favorite by user.
    fun showDataFromDatabase(context: Context){
        CoroutineScope(Dispatchers.IO).async {
            val dataBase : AppDataBase = Room.databaseBuilder(context , AppDataBase::class.java , Constants.DATA_BASE_NAME).build()
            CoroutineScope(Dispatchers.Main).async {
                jobSaveResultLiveData.value = dataBase.jobDao().selectAllFromFavorite()

            }
        }
    }

    // function for delete favorite data.
    fun deleteFavoriteData(context: Context , title : String){
        CoroutineScope(Dispatchers.IO).launch {
            var dataBase : AppDataBase = Room.databaseBuilder(context , AppDataBase::class.java , Constants.DATA_BASE_NAME).build()
            CoroutineScope(Dispatchers.Main).launch {
                dataBase.jobDao().unFavoriteJob(title)
            }
        }
    }

    // check select on favorite button.
    fun checkSelect(context: Context , title : String , toggleButton: ToggleButton){
        CoroutineScope(Dispatchers.IO).launch {
            var dataBase : AppDataBase = Room.databaseBuilder(context,AppDataBase::class.java, Constants.DATA_BASE_NAME).build()
            CoroutineScope(Dispatchers.Main).launch {
                var allResult =  dataBase.jobDao().selectAllFromFavorite()
                for(item in allResult){
                    if( item.title == title){
                        toggleButton.isChecked = true
                    }
                }
            }
        }
    }
}