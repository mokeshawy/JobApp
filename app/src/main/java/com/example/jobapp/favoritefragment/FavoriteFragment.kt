package com.example.jobapp.favoritefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.jobapp.R
import com.example.jobapp.adapter.RecyclerFavoriteAdapter
import com.example.jobapp.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    lateinit var binding            : FragmentFavoriteBinding
    private val favoriteViewModel   : FavoriteViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // connect with view model.
        binding.lifecycleOwner = this
        binding.favoriteVarViewModel = favoriteViewModel

        favoriteViewModel.showDataFromDatabase(requireActivity())
        favoriteViewModel.jobResultLiveData.observe(viewLifecycleOwner, Observer {
            binding.rvJobsList.adapter = RecyclerFavoriteAdapter(it,requireActivity())
        })
    }
}