package com.example.jobapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobapp.databinding.JobsListItemBinding
import com.example.jobapp.model.FavoriteJobModel
import com.example.jobapp.onclickforadapter.OnClickFavoriteAdapter
import com.squareup.picasso.Picasso

class RecyclerFavoriteAdapter ( private var mFavoriteJobModel: ArrayList<FavoriteJobModel> ,
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

    fun update( newData : List<FavoriteJobModel>){
        mFavoriteJobModel.clear()
        mFavoriteJobModel.addAll(newData)
        notifyDataSetChanged()
    }
}