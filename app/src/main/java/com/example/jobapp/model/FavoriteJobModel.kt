package com.example.jobapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class FavoriteJobModel(

    @ColumnInfo(name = "company")
    val company         : String,

    @ColumnInfo(name = "company_logo")
    val company_logo    : String? = null,

    @ColumnInfo(name = "company_url")
    val company_url     : String? = null,

    @ColumnInfo(name = "description")
    val description     : String,

    @ColumnInfo(name = "type")
    val type            : String,

    @ColumnInfo(name = "url")
    val url             : String? = null,

    @ColumnInfo(name = "title")
    val title           : String,

): Serializable
{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}