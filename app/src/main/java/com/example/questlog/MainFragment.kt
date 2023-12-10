package com.example.questlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_games).setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_gamesFragment)
        }
        view.findViewById<Button>(R.id.button_lists).setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_listsFragment)
        }
        view.findViewById<Button>(R.id.button_reviews).setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_reviewsFragment)
        }
    }
}
