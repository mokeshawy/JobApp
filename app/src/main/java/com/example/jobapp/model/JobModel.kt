package com.example.jobapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JobModel(

    @ColumnInfo(name = "company")
    val company         : String,

    @ColumnInfo(name = "company_logo")
    val company_logo    : String,

    @ColumnInfo(name = "title")
    val title           : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}