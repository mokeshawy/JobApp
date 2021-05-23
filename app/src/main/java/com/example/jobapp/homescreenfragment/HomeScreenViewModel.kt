package com.example.jobapp.homescreenfragment


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.jobapp.model.JobModel
import com.example.jobapp.retrofit.RetrofitBuilder
import com.example.jobapp.response.JobsResponse
import com.example.jobapp.roomdatabase.AppDataBase
import com.example.jobapp.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {


    val jobResultLiveData = MutableLiveData<List<JobsResponse>>()
    // get data from api and show in home screen page.
    fun getJobResult(){

        CoroutineScope(Dispatchers.IO).async {

            val response = RetrofitBuilder.getJobApi().getJobList(Constants.API_KEY)

            CoroutineScope(Dispatchers.Main).async {

                jobResultLiveData.value = response.body()

            }
        }
    }

    // insert data to room database
    fun insertJobToDatabase( context: Context ,
                             jobsResponse : JobsResponse){

        CoroutineScope(Dispatchers.IO).launch {
            var dataBase : AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java , Constants.TABLE_NAME).build()
            CoroutineScope(Dispatchers.Main).launch {
                var title = dataBase.jobDao().selectByTitle(jobsResponse.title)
                if( title.size == 1){
                    // no replay save item
                }else{
                    dataBase.jobDao().insertFavoriteJob(
                        JobModel(jobsResponse.company,
                            jobsResponse.company_logo,
                            jobsResponse.company_url,
                            jobsResponse.description,
                            jobsResponse.type,
                            jobsResponse.url,
                            jobsResponse.title)
                    )
                }
            }
        }
    }
}