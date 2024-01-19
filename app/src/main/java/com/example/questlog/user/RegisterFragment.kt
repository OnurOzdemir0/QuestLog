package com.example.questlog.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.questlog.R
import com.example.questlog.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding  : FragmentRegisterBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_register,container,false)

        val navController = findNavController()
        binding.backToLogInButton.setOnClickListener{
            navController.navigate(R.id.action_registerFragment_to_logInFragment)
        }

        binding.registerButton.setOnClickListener{
            //TODO upload user to data base
            navController.navigate(R.id.action_registerFragment_to_logInFragment)
        }
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

}