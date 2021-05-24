package com.example.jobapp.searchfragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jobapp.R
import com.example.jobapp.adapter.SearchAdapter
import com.example.jobapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    lateinit var binding        : FragmentSearchBinding
    private val searchViewModel : SearchViewModel by viewModels()
    private val myAdapter       : SearchAdapter by lazy { SearchAdapter() }



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

        // if we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)

        searchViewModel.readData(requireActivity()).observe(viewLifecycleOwner,{
            binding.searchJobList.adapter = myAdapter
            myAdapter.setData(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu,menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        searchDatabase(query!!)
        return true
    }

    private fun searchDatabase(query : String){
        val searchQuery = "%$query%"

        searchViewModel.searchDatabase(requireActivity(),searchQuery).observe(viewLifecycleOwner,{ list ->
            list.let {
                myAdapter.setData(it)
            }
        })
    }
}