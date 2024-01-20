package com.example.questlog.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.questlog.R
import com.example.questlog.databinding.FragmentLogInBinding
import com.example.questlog.user.viewmodel.UserViewModel

class LogInFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val navController = findNavController()
        val binding: FragmentLogInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        binding.logInButton.setOnClickListener{
            val username = binding.logInUserName.text.toString()
            val password = binding.LogInPassword.text.toString()
            userViewModel.tryToLogInUser(username, password)
            userViewModel.currentUser.observe(viewLifecycleOwner) { loginSuccess ->
                if (loginSuccess != null) {
                    Log.d("LogInFragment", "Login success")
                    navController.navigate(R.id.action_logInFragment_to_gamesFragment)
                    userViewModel.currentUser.removeObservers(viewLifecycleOwner)
                } else {
                    Log.d("LogInFragment", "Login failed")
                }
            }
        }
        binding.loginRegisterButton.setOnClickListener{
            navController.navigate(R.id.action_logInFragment_to_registerFragment)
        }
        return binding.root
    }
}