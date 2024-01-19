package com.example.questlog.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.questlog.R
import com.example.questlog.databinding.FragmentProfileBinding
import com.example.questlog.playlist.viewmodel.PlaylistViewModel
import com.example.questlog.user.viewmodel.UserViewModel


class ProfileFragment : Fragment() {


    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentProfileBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile,
            container, false
        )
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        binding.profileViewModel = userViewModel

        return  binding.root
    }


}