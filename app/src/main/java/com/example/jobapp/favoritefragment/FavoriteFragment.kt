package com.example.jobapp.favoritefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.jobapp.R
import com.example.jobapp.adapter.RecyclerFavoriteAdapter
import com.example.jobapp.databinding.FragmentFavoriteBinding
import com.example.jobapp.model.JobModel
import com.example.jobapp.onclickforadapter.OnClickFavoriteAdapter
import com.example.jobapp.roomdatabase.AppDataBase
import com.example.jobapp.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() , OnClickFavoriteAdapter{

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
        favoriteViewModel.jobSaveResultLiveData.observe(viewLifecycleOwner, Observer {
            binding.rvJobsList.adapter = RecyclerFavoriteAdapter(it,this)
        })
    }

    override fun onClickFavoritePage(
        viewHolder: RecyclerFavoriteAdapter.ViewHolder,
        jobModel: JobModel,
        position: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            var dataBase : AppDataBase = Room.databaseBuilder(requireActivity(), AppDataBase::class.java , Constants.TABLE_NAME).build()
            CoroutineScope(Dispatchers.Main).launch {
                dataBase.jobDao().selectAllJob()
            }
        }
    }
}