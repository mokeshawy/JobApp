package com.example.jobapp.favoritefragment

import android.os.Bundle
import android.os.Handler
import android.util.SparseBooleanArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.jobapp.R
import com.example.jobapp.adapter.RecyclerFavoriteAdapter
import com.example.jobapp.databinding.FragmentFavoriteBinding
import com.example.jobapp.model.FavoriteJobModel
import com.example.jobapp.model.JobModel
import com.example.jobapp.onclickforadapter.OnClickFavoriteAdapter
import com.example.jobapp.roomdatabase.AppDataBase
import com.example.jobapp.roomdatabase.DatabaseModule
import com.example.jobapp.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() , OnClickFavoriteAdapter{

    lateinit var binding            : FragmentFavoriteBinding
    private val favoriteViewModel   : FavoriteViewModel by viewModels()
    var favoriteAdapter = RecyclerFavoriteAdapter(arrayListOf(),this)
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

        binding.rvJobsList.adapter = favoriteAdapter


        // show data from favorite from database.
        favoriteViewModel.showDataFromDatabase(requireActivity()).observe(viewLifecycleOwner, Observer {
             favoriteAdapter.update(it)
            // show recycler when found data and hide when not found.
            Constants.showRecycler(it,binding.rvJobsList,binding.tvFavoriteNotFound)
        })
    }

    override fun onClickFavoritePage(
        viewHolder: RecyclerFavoriteAdapter.ViewHolder,
        favoriteJobModel: FavoriteJobModel,
        position: Int
    ) {
        // call check select on favorite button.
        favoriteViewModel.checkSelect(requireActivity(),
            favoriteJobModel.title,
            viewHolder.binding.btnFavoriteJobs)

        // make onClick itemView.
        viewHolder.binding.btnFavoriteJobs.setOnClickListener {
            // call function for delete job from favorite
            favoriteViewModel.deleteFavoriteData(requireActivity(),favoriteJobModel.title)
            // when dun favorite item will remove from list.
            favoriteViewModel.showDataFromDatabase(requireActivity()).observe(viewLifecycleOwner, Observer {
                favoriteAdapter.update(it)
                // show recycler when found data and hide when not found.
                Constants.showRecycler(it,binding.rvJobsList,binding.tvFavoriteNotFound)
            })
        }
        // go details job with favoriteJobModel object.
        viewHolder.itemView.setOnClickListener {
            var action = FavoriteFragmentDirections.actionFavoriteFragmentToFavoriteDetailsFragment(favoriteJobModel)
            findNavController().navigate(action)
        }
    }
}