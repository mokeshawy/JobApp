package com.example.jobapp.homescreenfragment

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
import com.example.jobapp.adapter.RecyclerJobsListAdapter
import com.example.jobapp.databinding.FragmentHomeScreenBinding
import com.example.jobapp.model.JobModel
import com.example.jobapp.onclickforadapter.OnClickHomeAdapter
import com.example.jobapp.response.JobsResponse
import com.example.jobapp.roomdatabase.AppDataBase
import com.example.jobapp.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreenFragment : Fragment() , OnClickHomeAdapter {

    lateinit var binding            : FragmentHomeScreenBinding
    private val homeScreenViewModel : HomeScreenViewModel by viewModels()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // connect with view model.
        binding.lifecycleOwner = this
        binding.homeScreenVarModel = homeScreenViewModel

        // show result from api.
        homeScreenViewModel.getJobResult()
        homeScreenViewModel.jobResultLiveData.observe(viewLifecycleOwner, Observer {
            binding.rvJobsList.adapter = RecyclerJobsListAdapter(it,this)

        })
    }

    // onClick adapter
    override fun onclickHomePage(
        viewHolder: RecyclerJobsListAdapter.ViewHolder,
        jobsResponse: JobsResponse,
        position: Int
    ) {

        // call function for insert data to room database.
      homeScreenViewModel.insertJobToDatabase(requireActivity(),jobsResponse)
    }
}