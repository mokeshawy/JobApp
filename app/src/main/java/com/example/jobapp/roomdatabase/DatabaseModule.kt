package com.example.jobapp.roomdatabase

import android.content.Context
import androidx.room.Room
import com.example.jobapp.util.Constants

object DatabaseModule {

    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context ,
        AppDataBase::class.java ,
        Constants.DATA_BASE_NAME).build()

}
