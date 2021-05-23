package com.example.jobapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JobModel(

    @ColumnInfo(name = "company")
    val company         : String,

){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}