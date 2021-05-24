package com.example.jobapp.favoritedetailsfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.jobapp.databinding.FragmentFavoriteDetailsBinding
import com.squareup.picasso.Picasso

class FavoriteDetailsFragment : Fragment() {

    lateinit var binding    : FragmentFavoriteDetailsBinding
    val favoriteJobModel    : FavoriteDetailsFragmentArgs by navArgs()
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // receive object from favorite page.
        binding.apply {
            Picasso.get().load(favoriteJobModel.favoriteJobModel.company_logo).into(ivCompanyLogo)
            tvCompanyName.text  = favoriteJobModel.favoriteJobModel.company
            tvJobTitle.text     = favoriteJobModel.favoriteJobModel.title
            tvJobType.text      = favoriteJobModel.favoriteJobModel.type
            tvJobUrl.text       = favoriteJobModel.favoriteJobModel.url
            tvCompanyUrl.text   = favoriteJobModel.favoriteJobModel.company_url
            tvJobDescription.text = favoriteJobModel.favoriteJobModel.description
        }

    }
}