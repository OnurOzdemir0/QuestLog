package com.example.questlog.playlist

import ReviewViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questlog.playlist.viewmodel.PlaylistViewModel

import androidx.navigation.fragment.findNavController

import com.example.questlog.R
import com.example.questlog.Review
import com.example.questlog.databinding.FragmentPlaylistBinding
import com.example.questlog.playlist.adapter.PlaylistAdapter
import com.example.questlog.playlist.adapter.PlaylistCallBacks
import com.example.questlog.playlist.GameStatus


class PlaylistFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var reviewViewModel: ReviewViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentPlaylistBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_playlist,
            container, false
        )

        binding.lifecycleOwner = this


        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)

        var adapter: PlaylistAdapter = PlaylistAdapter(
            PlaylistCallBacks(
            onStatusChange = { item, status -> OnStatusChange(item, status) },
            onReviewClick = { item -> OnReviewClick(item) },
            onScoreClick = { item, score -> OnScoreChange(item, score) }
        )
        )
        binding.playlistRecyclerview.adapter = adapter
        binding.playlistRecyclerview.layoutManager = LinearLayoutManager(context)
        reviewViewModel = ViewModelProvider(requireActivity()).get(ReviewViewModel::class.java)
        // Inflate the layout for this fragment

        adapter.submitList(playlistViewModel.getDataList())
        return binding.root
    }

    private fun OnStatusChange(item: PlayListItem?, status: GameStatus) {
        playlistViewModel.changePlayListItemStatus(item, status)
    }

    private fun OnReviewClick(item: PlayListItem?) {
        if (item == null) return
        val navController = findNavController()
        var doesExist = false
        for (item in reviewViewModel.getReviewList()) {
            if (item.game.gameID == item.game.gameID)
                doesExist = true
        }
        if (!doesExist) {
            reviewViewModel.addNewReview(Review(item.game, "", false))
            navController.navigate(R.id.action_playlist2_to_reviewsFragment)
        }
    }

    private fun OnScoreChange(item: PlayListItem?, score: Int) {
        playlistViewModel.changePlayListItemUserScore(item, score)
    }
}