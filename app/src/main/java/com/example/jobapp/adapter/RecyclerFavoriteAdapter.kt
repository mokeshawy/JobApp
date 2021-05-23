package com.example.jobapp.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.jobapp.databinding.JobsListItemBinding
import com.example.jobapp.model.FavoriteJobModel
import com.example.jobapp.model.JobModel
import com.example.jobapp.onclickforadapter.OnClickFavoriteAdapter

import com.example.jobapp.response.JobsResponse
import com.example.jobapp.roomdatabase.AppDataBase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecyclerFavoriteAdapter ( private var mFavoriteJobModel: List<FavoriteJobModel> ,
                                var onClickFavoriteAdapter: OnClickFavoriteAdapter) : RecyclerView.Adapter<RecyclerFavoriteAdapter.ViewHolder>() {

    class ViewHolder(var binding : JobsListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // initialize onClickUsersAdapter from interface
        fun initialize(viewHolder: ViewHolder, favoriteJobModel: FavoriteJobModel, action : OnClickFavoriteAdapter){
            action.onClickFavoritePage(viewHolder , favoriteJobModel , adapterPosition)
        }

    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        // Create a new view, which defines the UI of the list item
        return ViewHolder(JobsListItemBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.binding.tvCompanyName.text   = mFavoriteJobModel[position].company
        viewHolder.binding.tvJobTitle.text      = mFavoriteJobModel[position].title
        Picasso.get().load(mFavoriteJobModel[position].company_logo).into(viewHolder.binding.ivCompanyLogo)

        // call fun initialize.
        viewHolder.initialize( viewHolder , mFavoriteJobModel[position] , onClickFavoriteAdapter)

    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = mFavoriteJobModel.size

}