package com.example.jobapp.detailsjobfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.jobapp.R
import com.example.jobapp.databinding.FragmentDetailsJobBinding
import com.example.jobapp.model.FavoriteJobModel
import com.example.jobapp.model.JobModel
import com.example.jobapp.response.JobsResponse
import com.example.jobapp.util.Constants
import com.squareup.picasso.Picasso

class DetailsJobFragment : Fragment() {


    lateinit var binding    : FragmentDetailsJobBinding
    var jobsResponse        : JobsResponse?     = null
    var jobModel            : JobModel?         = null
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsJobBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // receive object from home screen for result api.
        if( arguments?.containsKey(Constants.BUNDLE_JOB_RESPONSE_KEY) == true){
            jobsResponse = arguments?.getSerializable(Constants.BUNDLE_JOB_RESPONSE_KEY) as JobsResponse
            binding.apply {
                Picasso.get().load(jobsResponse!!.company_logo).into(ivCompanyLogo)
                tvCompanyName.text  = jobsResponse!!.company
                tvJobTitle.text     = jobsResponse!!.title
                tvJobType.text      = jobsResponse!!.type
                tvJobUrl.text       = jobsResponse!!.url
                tvCompanyUrl.text   = jobsResponse!!.company_url
                tvJobDescription.text = jobsResponse!!.description
            }
        }



        // receive object from home screen for result room database when internet connection offline.
        if( arguments?.containsKey(Constants.BUNDLE_JOB_MODEL_KEY) == true) {
            jobModel = arguments?.getSerializable(Constants.BUNDLE_JOB_MODEL_KEY) as JobModel
            binding.apply {
                Picasso.get().load(jobModel!!.company_logo).into(ivCompanyLogo)
                tvCompanyName.text = jobModel!!.company
                tvJobTitle.text = jobModel!!.title
                tvJobType.text = jobModel!!.type
                tvJobUrl.text = jobModel!!.url
                tvCompanyUrl.text = jobModel!!.company_url
                tvJobDescription.text = jobModel!!.description
            }
        }
    }
}