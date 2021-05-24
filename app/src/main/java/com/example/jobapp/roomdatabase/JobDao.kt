package com.example.jobapp.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jobapp.model.FavoriteJobModel
import com.example.jobapp.model.JobModel
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {

    // query job model.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResultJob( jobModel: JobModel )

    @Query("SELECT * FROM JobModel WHERE title = :title")
    suspend fun selectByTitle(title : String) : List<JobModel>

    @Query("SELECT * FROM JobModel")
    fun selectAllJob() : Flow<List<JobModel>>

    @Query("DELETE FROM JobModel WHERE title = :title")
    suspend fun deleteJob( title: String )

    @Query("SELECT * FROM JobModel WHERE title LIKE :searchQuery OR company LIKE :searchQuery")
    fun searchDatabase(searchQuery : String) : Flow<List<JobModel>>

    @Query("SELECT * FROM JobModel ORDER BY id ASC")
    fun readData(): Flow<List<JobModel>>
    // ================================================================ //

    // query favorite job model.
    @Insert
    suspend fun addFavoriteJob( favoriteJobModel: FavoriteJobModel)

    @Query("SELECT * FROM FavoriteJobModel")
    suspend fun selectAllFromFavorite() : List<FavoriteJobModel>

    @Query("SELECT * FROM FavoriteJobModel WHERE title = :title")
    suspend fun selectFavoriteByTitle(title : String) : List<FavoriteJobModel>

    @Query("DELETE FROM FavoriteJobModel WHERE title = :title")
    suspend fun unFavoriteJob( title: String )
}