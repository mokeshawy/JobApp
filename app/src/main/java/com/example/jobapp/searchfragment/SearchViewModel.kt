package com.example.jobapp.searchfragment


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.jobapp.roomdatabase.DatabaseModule
import com.example.jobapp.model.JobModel

class SearchViewModel : ViewModel() {

    //Show data from database in search fragment page.
    fun readData(context: Context) : LiveData<List<JobModel>> {
        return DatabaseModule.provideDatabase(context).jobDao().readData().asLiveData()
    }

    // search job in room database.
    fun searchDatabase(context: Context, searchQuery : String) : LiveData<List<JobModel>>{
        return DatabaseModule.provideDatabase(context).jobDao().searchDatabase(searchQuery).asLiveData()
    }
}