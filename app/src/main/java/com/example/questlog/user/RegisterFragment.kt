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
import com.example.questlog.databinding.FragmentRegisterBinding
import com.example.questlog.user.viewmodel.UserViewModel

class RegisterFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val navController = findNavController()
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        binding.registerButton.setOnClickListener {
            val username = binding.registerUsername.text.toString()
            val password = binding.passwordFirst.text.toString()
            userViewModel.tryToSignUpUser(username, password)
            userViewModel.currentUser.observe(viewLifecycleOwner) { signUpSuccess ->
                if (signUpSuccess != null) {
                    Log.d("RegisterFragment", "Registration success")
                    navController.navigate(R.id.action_registerFragment_to_logInFragment)
                    userViewModel.currentUser.removeObservers(viewLifecycleOwner)
                } else {
                    Log.d("RegisterFragment", "Registration failed")
                }
            }
        }

        binding.backToLogInButton.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_logInFragment)
        }
        return binding.root
    }
}
