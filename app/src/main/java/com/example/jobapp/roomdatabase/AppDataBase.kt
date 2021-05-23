package com.example.jobapp.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jobapp.model.FavoriteJobModel
import com.example.jobapp.model.JobModel
import com.example.jobapp.response.JobsResponse

@Database(entities = [JobModel::class , FavoriteJobModel::class] , version = 2)
abstract class AppDataBase : RoomDatabase() {
    abstract fun jobDao() : JobDao
}