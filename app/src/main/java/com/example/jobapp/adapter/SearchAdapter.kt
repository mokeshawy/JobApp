package com.example.jobapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobapp.databinding.JobsListItemBinding
import com.example.jobapp.model.JobModel
import com.example.jobapp.onclickforadapter.OnClickSearchAdapter
import com.squareup.picasso.Picasso

class SearchAdapter ( var onClickSearch : OnClickSearchAdapter) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var oldData = emptyList<JobModel>()

    class ViewHolder(var binding : JobsListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // initialize onClickUsersAdapter from interface
        fun initialize(viewHolder: ViewHolder, jobModel : JobModel, action : OnClickSearchAdapter){
            action.onClickSearchResult(viewHolder , jobModel , adapterPosition)
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

        viewHolder.binding.tvCompanyName.text   = oldData[position].company
        viewHolder.binding.tvJobTitle.text      = oldData[position].title
        Picasso.get().load(oldData[position].company_logo).into(viewHolder.binding.ivCompanyLogo)

        // call fun initialize.
        viewHolder.initialize( viewHolder , oldData[position] , onClickSearch)

    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = oldData.size

    fun setData(newData: List<JobModel>){
        oldData = newData
        notifyDataSetChanged()
    }
}