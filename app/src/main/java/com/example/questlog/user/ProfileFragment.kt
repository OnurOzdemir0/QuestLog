package com.example.questlog.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.questlog.R
import com.example.questlog.databinding.FragmentProfileBinding
import com.example.questlog.playlist.GameStatus
import com.example.questlog.playlist.viewmodel.PlaylistViewModel
import com.example.questlog.review.viewmodel.ReviewViewModel
import com.example.questlog.user.viewmodel.UserViewModel


class ProfileFragment : Fragment() {


    private lateinit var userViewModel: UserViewModel
    private lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var reviewViewModel: ReviewViewModel

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
        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        reviewViewModel = ViewModelProvider(requireActivity()).get(ReviewViewModel::class.java)

        userViewModel.currentUser.observe(viewLifecycleOwner){
            binding.profileUserName.text = it?.userName
        }



        binding.profileViewModel = userViewModel
        val list = getPlaylistStats()
        binding.profileGamesPlayingByUser.text = ""+list[0]
        binding.profileGamesFinishedByUser.text = ""+ list[1]
        binding.profileGamesReviewedByUser.text = "" + reviewViewModel.getReviewList().size
        binding.profileChangePasswordButton.setOnClickListener{
            val navController = findNavController()
            navController.navigate(R.id.action_profileFragment2_to_changePasswordFragment)

        }
        return  binding.root
    }


    fun getPlaylistStats() : List<Int>{
        var playing : Int = 0
        var finished :Int = 0
        for(p in playlistViewModel.getDataList()){
            when(p.gameStatus){
                GameStatus.Playing -> playing ++
                GameStatus.Finished-> finished++
                else ->continue
            }
        }

        return listOf(playing,finished)
    }

}