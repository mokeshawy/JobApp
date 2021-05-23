package com.example.jobapp.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jobapp.model.JobModel

@Dao
interface JobDao {

    @Insert
    suspend fun insertFavoriteJob( jobModel: JobModel)

    @Query("SELECT * FROM JobModel WHERE title = :title")
    suspend fun selectByTitle(title : String) : List<JobModel>

    @Query("SELECT * FROM JobModel")
    suspend fun selectAllNews() : List<JobModel>

    @Query("DELETE FROM JobModel WHERE title = :title")
    suspend fun deleteItems( title: String )
}