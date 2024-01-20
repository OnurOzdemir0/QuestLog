package com.example.questlog.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.questlog.R
import com.example.questlog.databinding.FragmentChangePasswordBinding
import com.example.questlog.user.viewmodel.UserViewModel


class ChangePasswordFragment : Fragment() {

    private  lateinit var  userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentChangePasswordBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_change_password,container,false)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        binding.changePasswordExit.setOnClickListener{
            val navController = findNavController()
            navController.navigate(R.id.action_changePasswordFragment_to_profileFragment2)
        }
        binding.changePasswordButton.setOnClickListener{
            val oldPassword = binding.changePasswordOld.text.toString()
            val newPassword = binding.changePasswordFirst.text.toString()
            val newPasswordCheck = binding.changePasswordSecond.text.toString()

            if(newPassword !=newPasswordCheck && newPassword.length>1){
                println("not equal passwords")
            }else{
                println("equal passwords")
                userViewModel.changePassword(oldPassword,newPassword){

                }
            }
        }
        return binding.root
    }


}