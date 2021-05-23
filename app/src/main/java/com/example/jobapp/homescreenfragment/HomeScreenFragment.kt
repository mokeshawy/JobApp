package com.example.jobapp.homescreenfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.room.Room
import com.example.jobapp.R
import com.example.jobapp.databinding.FragmentHomeScreenBinding
import com.example.jobapp.model.JobModel
import com.example.jobapp.roomdatabase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreenFragment : Fragment() {

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

//        CoroutineScope(Dispatchers.IO).launch {
//            var dataBase : AppDataBase = Room.databaseBuilder(requireActivity(), AppDataBase::class.java , "job").build()
//            CoroutineScope(Dispatchers.Main).launch {
//                dataBase.jobDao().insertFavoriteJob(JobModel("Programing","Mohamed","Programing"))
//            }
//        }
    }
}