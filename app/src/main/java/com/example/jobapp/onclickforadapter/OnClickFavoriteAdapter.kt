package com.example.jobapp.onclickforadapter

import com.example.jobapp.adapter.RecyclerFavoriteAdapter
import com.example.jobapp.model.JobModel

interface OnClickFavoriteAdapter {

    fun onClickFavoritePage( viewHolder: RecyclerFavoriteAdapter.ViewHolder , jobModel: JobModel , position : Int)
}