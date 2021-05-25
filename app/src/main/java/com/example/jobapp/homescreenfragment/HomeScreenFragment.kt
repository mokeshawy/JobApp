package com.example.jobapp.homescreenfragment

import android.app.Service
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.SparseBooleanArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.jobapp.R
import com.example.jobapp.adapter.RecyclerJobsListAdapter
import com.example.jobapp.adapter.RecyclerSaveResultAdapter
import com.example.jobapp.databinding.FragmentHomeScreenBinding
import com.example.jobapp.model.JobModel
import com.example.jobapp.onclickforadapter.OnClickHomeAdapter
import com.example.jobapp.onclickforadapter.OnClickSaveResultAdpter
import com.example.jobapp.response.JobsResponse
import com.example.jobapp.util.Constants

@Suppress("DEPRECATION")
class HomeScreenFragment : Fragment(),
    OnClickHomeAdapter,
    OnClickSaveResultAdpter{

    lateinit var binding            : FragmentHomeScreenBinding
    private val homeScreenViewModel : HomeScreenViewModel by viewModels()
    var connectivity    : ConnectivityManager? = null
    var info            : NetworkInfo? = null

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

        // Show progress dialog.
        Constants.showProgressDialog(resources.getString(R.string.please_wait) ,requireActivity())
        // check internet connection.
        connectivity = requireActivity().getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(connectivity != null){
            info = connectivity!!.activeNetworkInfo
                if(info != null){
                    if(info!!.state == NetworkInfo.State.CONNECTED){

                        // show result from api in recycler adapter.
                        homeScreenViewModel.getJobResult()
                        homeScreenViewModel.jobResultLiveData.observe(viewLifecycleOwner, Observer {
                            binding.rvJobsList.adapter = RecyclerJobsListAdapter(it,this)

                            // show recycler when found data and hide when not found.
                            Constants.showRecycler(it,binding.rvJobsList,binding.tvJobNotFound)

                            //hide progress dialog.
                            Constants.hideProgressDialog()

                        })
                    }
                }else{
                    // get data from database when no internet connection.
                    homeScreenViewModel.getSaveDate(requireActivity()).observe(viewLifecycleOwner,Observer{
                        binding.rvJobsList.adapter = RecyclerSaveResultAdapter(it,this)

                        // show recycler when found data and hide when not found.
                        Constants.showRecycler(it,binding.rvJobsList,binding.tvJobNotFound)
                        Constants.hideProgressDialog()
                        Toast.makeText(requireActivity(),"no internet connection",Toast.LENGTH_SHORT).show()
                    })
            }
        }
    }

    // onClick adapter
    override fun onclickHomePage(
        viewHolder: RecyclerJobsListAdapter.ViewHolder,
        mJobsResponse: List<JobsResponse>,
        jobsResponse: JobsResponse,
        position: Int) {

        // call function for insert data to room database.
        homeScreenViewModel.insertJobToDatabase(requireActivity(),jobsResponse)

        // call check select on favorite button.
        homeScreenViewModel.checkSelect(requireActivity(),
            jobsResponse.title,
            viewHolder.binding.btnFavoriteJobs)


        val checkBoxArray = SparseBooleanArray()
        viewHolder.binding.btnFavoriteJobs.isChecked = checkBoxArray.get( position , false)
        // make onClick itemView.
        viewHolder.binding.btnFavoriteJobs.setOnClickListener {

            if(!checkBoxArray.get( position , false)){
                viewHolder.binding.btnFavoriteJobs.isChecked = true
                checkBoxArray.put(position , true)
                // call function for add job to favorite.
                homeScreenViewModel.addFavoriteJob(requireActivity(),
                    jobsResponse)
            }else{
                viewHolder.binding.btnFavoriteJobs.isChecked = false
                checkBoxArray.put(position , false)
                // call function unFavorite.
                homeScreenViewModel.unFavoriteJob(requireActivity(),
                    jobsResponse.title)
            }
        }

        // go details job with jobsResponse object.
        viewHolder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(Constants.BUNDLE_JOB_RESPONSE_KEY,jobsResponse)
            findNavController().navigate(R.id.action_homeScreenFragment_to_detailsJobFragment,bundle)
        }
    }

    // onClick for save data from database.
    override fun onClickSaveResult(
        viewHolder: RecyclerSaveResultAdapter.ViewHolder,
        jobModel: JobModel,
        position: Int
    ) {

        // call check select on favorite button.
        homeScreenViewModel.checkSelect(requireActivity(),
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
                homeScreenViewModel.addFavoriteJobFromDatabase(requireActivity(), jobModel)
            }else{
                viewHolder.binding.btnFavoriteJobs.isChecked = false
                checkBoxArray.put(position , false)
                // call function unFavorite from database.
                homeScreenViewModel.unFavoriteJobFromDatabase(requireActivity(),
                    jobModel.title)
            }
        }

        // go details job with jobModel object.
        viewHolder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(Constants.BUNDLE_JOB_MODEL_KEY,jobModel)
            findNavController().navigate(R.id.action_homeScreenFragment_to_detailsJobFragment,bundle)
        }
    }
}