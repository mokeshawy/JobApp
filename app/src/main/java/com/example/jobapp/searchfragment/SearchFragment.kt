package com.example.jobapp.searchfragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.SparseBooleanArray
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.jobapp.R
import com.example.jobapp.adapter.SearchAdapter
import com.example.jobapp.databinding.FragmentSearchBinding
import com.example.jobapp.model.JobModel
import com.example.jobapp.onclickforadapter.OnClickSearchAdapter
import com.example.jobapp.util.Constants

class SearchFragment : Fragment(),
    OnClickSearchAdapter{

    lateinit var binding        : FragmentSearchBinding
    private val searchViewModel : SearchViewModel by viewModels()
    private val searchAdapter   : SearchAdapter by lazy { SearchAdapter(this) }



    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // connect with view model.
        binding.lifecycleOwner = this
        binding.searchVarModel = searchViewModel



        // call function readData fro oldData.
        searchViewModel.readData(requireActivity()).observe(viewLifecycleOwner, Observer{
            binding.rvSearchList.adapter = searchAdapter
            searchAdapter.setData(it)

            // show recycler when found data and hide when not found.
            Constants.showRecycler(it,binding.rvSearchList,binding.tvJobNotFound)
        })

        // call function fro get data from database after entry company name or title for job
        searchViewModel.searchDatabase(requireActivity(),binding.etSearchJobList.text.toString())
        binding.etSearchJobList.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                search(binding.etSearchJobList.text.toString())
            }
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

    }

    // function search on job.
    private fun search(query : String){
        val searchQuery = "%$query%"
        searchViewModel.searchDatabase(requireContext(),searchQuery).observe(viewLifecycleOwner,Observer{ list ->
            list.let {
                searchAdapter.setData(it)
                Constants.showRecycler(it,binding.rvSearchList,binding.tvJobNotFound)
            }
        })
    }
    // onClick for search adapter.
    override fun onClickSearchResult(
        viewHolder: SearchAdapter.ViewHolder,
        jobModel: JobModel,
        position: Int
    ) {

        // call check select on favorite button.
        searchViewModel.checkSelect(requireActivity(),
            jobModel.title,
            viewHolder.binding.btnFavoriteJobs)


        val checkBoxArray = SparseBooleanArray()
        viewHolder.binding.btnFavoriteJobs.isChecked = checkBoxArray.get( position , false)
        // make onClick itemView.
        viewHolder.binding.btnFavoriteJobs.setOnClickListener {

            if(!checkBoxArray.get( position , false)){
                viewHolder.binding.btnFavoriteJobs.isChecked = true
                checkBoxArray.put(position , true)
                // call function for add job from database to favorite
                searchViewModel.addFavoriteJobFromDatabase(requireActivity(), jobModel)
            }else{
                viewHolder.binding.btnFavoriteJobs.isChecked = false
                checkBoxArray.put(position , false)
                // call function unFavorite from database.
                searchViewModel.unFavoriteJobFromDatabase(requireActivity(),
                    jobModel.title)
            }
        }
        // go to details page from search page show details for any job search.
        viewHolder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(Constants.BUNDLE_JOB_MODEL_KEY,jobModel)
            findNavController().navigate(R.id.action_searchFragment_to_detailsJobFragment,bundle)
        }
    }
}