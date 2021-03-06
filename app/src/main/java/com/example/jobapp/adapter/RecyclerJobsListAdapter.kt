package com.example.jobapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobapp.databinding.JobsListItemBinding
import com.example.jobapp.onclickforadapter.OnClickHomeAdapter
import com.example.jobapp.response.JobsResponse
import com.squareup.picasso.Picasso


class RecyclerJobsListAdapter ( private var mJobs: List<JobsResponse> ,
                                var onClickHomeAdapter: OnClickHomeAdapter) : RecyclerView.Adapter<RecyclerJobsListAdapter.ViewHolder>() {

    class ViewHolder(var binding : JobsListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // initialize onClickUsersAdapter from interface
        fun initialize(viewHolder: ViewHolder, mJobs: List<JobsResponse> , jobsResponse: JobsResponse, action : OnClickHomeAdapter){
            action.onclickHomePage(viewHolder , mJobs , jobsResponse , adapterPosition )
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



        viewHolder.binding.tvCompanyName.text   = mJobs[position].company
        viewHolder.binding.tvJobTitle.text      = mJobs[position].title
        Picasso.get().load(mJobs[position].company_logo).into(viewHolder.binding.ivCompanyLogo)

        viewHolder.initialize( viewHolder , mJobs , mJobs[position] , onClickHomeAdapter)

    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = mJobs.size

}