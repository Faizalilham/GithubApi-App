package com.example.githubapi.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.githubapi.R
import com.example.githubapi.databinding.FragmentLoginBinding
import com.example.githubapi.model.UserLogin
import com.example.githubapi.room.SetupRoom
import com.example.githubapi.util.Constant
import com.example.githubapi.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var authViewModel: AuthViewModel
    private val db by lazy{
        SetupRoom(requireActivity())
    }
//    private val listData : MutableList<UserLogin> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
        }
        sharedPreferences = requireContext().getSharedPreferences(Constant.SHARE_PREFERENCE,Context.MODE_PRIVATE)
        doLogin()
        toRegister()
    }

    private fun doLogin(){
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if(username.isNotBlank() && password.isNotBlank() ){
                authViewModel.doSignIn(username,password)
                authViewModel.signIn.observe(requireActivity()){
                  if(it != null){
                      addToSharedPreferences(it.token,it.id,it.name,it.username,it.email,it.password)
                      setToast("Success","Horray Login Success, Hallo ${it.name}",MotionToastStyle.SUCCESS)
                      Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
                  }
                }
            }else{
                setToast("Warning !","Field cannot be empety",MotionToastStyle.WARNING)
            }

        }

    }

    private fun addToSharedPreferences(token :String,id:Int,name:String,username:String,email:String,password:String){
        sharedPreferences.edit().apply {
            putString(Constant.TOKEN,token)
            putInt(Constant.ID_USER,id)
            putString(Constant.NAME,name)
            putString(Constant.USERNAME,username)
            putString(Constant.EMAIL,email)
            putString(Constant.PASSWORD,password)
            apply()
        }
    }

    private fun toRegister(){
        binding.tvRegister.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.registerFragment)
        }
    }

    fun setToast(tittle : String,message :String,style : MotionToastStyle){
        MotionToast.createToast(requireActivity(), tittle,
            message,
            style,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(requireActivity(),R.font.poppins_regular))
    }


}