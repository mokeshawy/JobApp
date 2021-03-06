package com.example.jobapp.response

import java.io.Serializable


data class JobsResponse(

    val company         : String,
    val company_logo    : String,
    val company_url     : String,
    val created_at      : String,
    val description     : String,
    val how_to_apply    : String,
    val id              : String,
    val location        : String,
    val title           : String,
    val type            : String,
    val url             : String
) : Serializable