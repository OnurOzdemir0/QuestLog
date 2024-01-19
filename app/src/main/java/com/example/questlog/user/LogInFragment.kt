package com.example.questlog.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.questlog.R
import com.example.questlog.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val navController = findNavController()
        val binding :  FragmentLogInBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_log_in,container,false)


        binding.logInButton.setOnClickListener{
            //TODO : CHECK LOGIN THAN DO IT

            navController.navigate(R.id.action_logInFragment_to_gamesFragment)
        }
        binding.loginRegisterButton.setOnClickListener{
            navController.navigate(R.id.action_logInFragment_to_registerFragment)
        }




        return binding.root


    }


}