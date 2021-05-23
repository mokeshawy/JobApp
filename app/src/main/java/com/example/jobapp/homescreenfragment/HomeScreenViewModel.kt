package com.example.jobapp.homescreenfragment


import android.content.Context
import android.widget.Toast
import android.widget.ToggleButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.jobapp.model.FavoriteJobModel
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

    // insert data to room database from result api.
    fun insertJobToDatabase( context: Context ,
                             jobsResponse : JobsResponse){

        CoroutineScope(Dispatchers.IO).launch {
            var dataBase : AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java , Constants.DATA_BASE_NAME).build()
            CoroutineScope(Dispatchers.Main).launch {
                var title = dataBase.jobDao().selectByTitle(jobsResponse.title)
                if( title.size == 1){
                    // when size == 1 not save item because already save.
                }else{
                    dataBase.jobDao().insertResultJob(
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

    // fun add favorite job for result data from api.
    fun addFavoriteJob(context: Context , jobsResponse: JobsResponse){

        CoroutineScope(Dispatchers.IO).launch{
            var dataBase : AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java,Constants.DATA_BASE_NAME).build()
            CoroutineScope(Dispatchers.Main).launch{
                var title = dataBase.jobDao().selectFavoriteByTitle(jobsResponse.title)
                if(title.size == 1){

                }else{
                    dataBase.jobDao().addFavoriteJob(
                        FavoriteJobModel(jobsResponse.company,
                        jobsResponse.company_logo,
                        jobsResponse.company_url,
                        jobsResponse.description,
                        jobsResponse.type,
                        jobsResponse.url,
                        jobsResponse.title)
                    )
                    Toast.makeText(context,"Favorite ${jobsResponse.title}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // function delete from favorite.
    fun unFavoriteJob(context: Context , title: String){

        CoroutineScope(Dispatchers.IO).launch {
            var dataBase : AppDataBase = Room.databaseBuilder(context,AppDataBase::class.java, Constants.DATA_BASE_NAME).build()
            CoroutineScope(Dispatchers.Main).launch {
                dataBase.jobDao().unFavoriteJob(title)
                Toast.makeText(context,"Un Favorite ${title}", Toast.LENGTH_SHORT).show()
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

    var getSaveDataLive = MutableLiveData<List<JobModel>>()
    // function get all data save from database.
    fun getSaveDate(context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            var dataBase : AppDataBase = Room.databaseBuilder(context,AppDataBase::class.java, Constants.DATA_BASE_NAME).build()
            CoroutineScope(Dispatchers.Main).launch {

                getSaveDataLive.value = dataBase.jobDao().selectAllJob()
            }
        }
    }

    // fun add for data from room database favorite when no connection with internet job.
    fun addFavoriteJobFromDatabase(context: Context , jobModel: JobModel){

        CoroutineScope(Dispatchers.IO).launch{
            var dataBase : AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java,Constants.DATA_BASE_NAME).build()
            CoroutineScope(Dispatchers.Main).launch{
                var title = dataBase.jobDao().selectByTitle(jobModel.title)
                if(title.size == 1){
                    // when size == 1 not save item because already save.
                }else{
                    dataBase.jobDao().addFavoriteJob(
                        FavoriteJobModel(jobModel.company,
                            jobModel.company_logo,
                            jobModel.company_url,
                            jobModel.description,
                            jobModel.type,
                            jobModel.url,
                            jobModel.title)
                    )
                    Toast.makeText(context,"Favorite ${jobModel.title}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // function delete from favorite for data from room database.
    fun unFavoriteJobFromDatabase(context: Context , title : String){

        CoroutineScope(Dispatchers.IO).launch {
            var dataBase : AppDataBase = Room.databaseBuilder(context,AppDataBase::class.java, Constants.DATA_BASE_NAME).build()
            CoroutineScope(Dispatchers.Main).launch {
                dataBase.jobDao().unFavoriteJob(title)
                Toast.makeText(context,"Un Favorite ${title}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}