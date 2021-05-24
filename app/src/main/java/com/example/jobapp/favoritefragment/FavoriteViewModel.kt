package com.example.jobapp.favoritefragment

import android.content.Context
import android.widget.ToggleButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobapp.roomdatabase.DatabaseModule
import com.example.jobapp.model.FavoriteJobModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel(){

    val jobSaveResultLiveData = MutableLiveData<List<FavoriteJobModel>>()
    // show result data from database for add to favorite by user.
    fun showDataFromDatabase(context: Context){
        CoroutineScope(Dispatchers.IO).async {
            val dataBase = DatabaseModule.provideDatabase(context)
            CoroutineScope(Dispatchers.Main).async {
                jobSaveResultLiveData.value = dataBase.jobDao().selectAllFromFavorite()

            }
        }
    }

    // function for delete favorite data.
    fun deleteFavoriteData(context: Context , title : String){
        CoroutineScope(Dispatchers.IO).launch {
            val dataBase = DatabaseModule.provideDatabase(context)
            CoroutineScope(Dispatchers.Main).launch {
                dataBase.jobDao().unFavoriteJob(title)
            }
        }
    }

    // check select on favorite button.
    fun checkSelect(context: Context , title : String , toggleButton: ToggleButton){
        CoroutineScope(Dispatchers.IO).launch {
            val dataBase = DatabaseModule.provideDatabase(context)
            CoroutineScope(Dispatchers.Main).launch {
                val allResult =  dataBase.jobDao().selectAllFromFavorite()
                for(item in allResult){
                    if( item.title == title){
                        toggleButton.isChecked = true
                    }
                }
            }
        }
    }
}