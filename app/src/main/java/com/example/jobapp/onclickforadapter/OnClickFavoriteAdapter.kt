package com.example.jobapp.onclickforadapter

import com.example.jobapp.adapter.RecyclerFavoriteAdapter
import com.example.jobapp.model.FavoriteJobModel

interface OnClickFavoriteAdapter {

    fun onClickFavoritePage( viewHolder: RecyclerFavoriteAdapter.ViewHolder,
                             favoriteJobModel: FavoriteJobModel,
                             position : Int)
}