package com.example.githubapi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.githubapi.R
import com.example.githubapi.databinding.FragmentRegisterBinding
import com.example.githubapi.model.UserLogin
import com.example.githubapi.room.SetupRoom
import com.example.githubapi.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private lateinit var authViewModel: AuthViewModel
    private val db by lazy{
        SetupRoom(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doRegister()
        toLogin()
    }

    private fun doRegister(){
        binding.btnRegister.setOnClickListener {
            val name = binding.etUser.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if(name.isNotBlank() && username.isNotBlank() && email.isNotBlank() && password.isNotBlank()){
                authViewModel.doSignUp(name,username,email,password)
                authViewModel.signUp.observe(requireActivity()){
                   if(it != null){
                       setToast("Success","Register Succesfully",MotionToastStyle.SUCCESS)
                       Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
                   }
                }
            }else{
                setToast("Warning !","Field cannot be empety",MotionToastStyle.WARNING)
            }
        }
    }

    private fun toLogin(){
        binding.tvLogin.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
        }
    }

    private fun setToast(tittle : String,message :String,style : MotionToastStyle){
        MotionToast.createToast(requireActivity(), tittle,
            message,
            style,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(requireActivity(),R.font.poppins_regular))
    }


}