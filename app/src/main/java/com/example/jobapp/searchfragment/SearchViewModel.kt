package com.example.jobapp.searchfragment


import android.content.Context
import android.widget.ToggleButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.jobapp.model.FavoriteJobModel
import com.example.jobapp.roomdatabase.DatabaseModule
import com.example.jobapp.model.JobModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    //Show data from database in search fragment page.
    fun readData(context: Context) : LiveData<List<JobModel>> {
        return DatabaseModule.provideDatabase(context).jobDao().readData().asLiveData()
    }

    // search job in room database.
    fun searchDatabase(context: Context, searchQuery : String) : LiveData<List<JobModel>>{
        return DatabaseModule.provideDatabase(context).jobDao().searchDatabase(searchQuery).asLiveData()
    }



    // check select on add favorite button.
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

    // fun add for data from room database to favorite when no connection with internet job.
    fun addFavoriteJobFromDatabase(context: Context , jobModel: JobModel){
        CoroutineScope(Dispatchers.IO).launch{
            val dataBase = DatabaseModule.provideDatabase(context)
            CoroutineScope(Dispatchers.Main).launch{
                val title = dataBase.jobDao().selectFavoriteByTitle(jobModel.title)
                if(title.size == 1){
                    // when size == 1 not save item because already save.
                }else{
                    dataBase.jobDao().addFavoriteJob( FavoriteJobModel(jobModel.company,
                        jobModel.company_logo,
                        jobModel.company_url,
                        jobModel.description,
                        jobModel.type,
                        jobModel.url,
                        jobModel.title)
                    )
                }
            }
        }
    }

    // function delete from favorite for data from room database.
    fun unFavoriteJobFromDatabase(context: Context , title : String){
        CoroutineScope(Dispatchers.IO).launch {
            val dataBase = DatabaseModule.provideDatabase(context)
            CoroutineScope(Dispatchers.Main).launch {
                dataBase.jobDao().unFavoriteJob(title)
            }
        }
    }
}