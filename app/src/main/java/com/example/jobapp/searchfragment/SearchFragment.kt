package com.example.jobapp.searchfragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.jobapp.adapter.SearchAdapter
import com.example.jobapp.databinding.FragmentSearchBinding
import com.example.jobapp.model.JobModel
import com.example.jobapp.onclickforadapter.OnClickSearchAdapter

class SearchFragment : Fragment() , OnClickSearchAdapter{

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
            }
        })
    }
    // onClick for search adapter.
    override fun onClickSearchResult(
        viewHolder: SearchAdapter.ViewHolder,
        jobModel: JobModel,
        position: Int
    ) {
        viewHolder.itemView.setOnClickListener {
            Toast.makeText(requireActivity(),jobModel.title,Toast.LENGTH_SHORT).show()
        }
    }
}