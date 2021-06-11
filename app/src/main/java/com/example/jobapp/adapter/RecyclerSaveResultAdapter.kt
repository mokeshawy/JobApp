package com.example.jobapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobapp.databinding.JobsListItemBinding
import com.example.jobapp.model.JobModel
import com.example.jobapp.onclickforadapter.OnClickSaveResultAdpter
import com.squareup.picasso.Picasso

class RecyclerSaveResultAdapter(private var mJobModel: List<JobModel> ,
                                var onClickSaveResult: OnClickSaveResultAdpter) : RecyclerView.Adapter<RecyclerSaveResultAdapter.ViewHolder>() {

    class ViewHolder(var binding : JobsListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // initialize onClickUsersAdapter from interface
        fun initialize(viewHolder: ViewHolder, jobModel : JobModel , action : OnClickSaveResultAdpter){
            action.onClickSaveResult(viewHolder , jobModel , adapterPosition)
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

        viewHolder.binding.tvCompanyName.text   = mJobModel[position].company
        viewHolder.binding.tvJobTitle.text      = mJobModel[position].title
        Picasso.get().load(mJobModel[position].company_logo).into(viewHolder.binding.ivCompanyLogo)

        // call fun initialize.
        viewHolder.initialize( viewHolder , mJobModel[position] , onClickSaveResult)

    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = mJobModel.size

}