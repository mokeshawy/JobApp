package com.example.jobapp.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import com.example.jobapp.model.JobModel

@Dao
interface JobDao {

    @Insert
    fun insertFavoriteJob( jobModel: JobModel)
}