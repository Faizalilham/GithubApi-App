package com.example.githubapi.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.githubapi.R
import com.example.githubapi.databinding.FragmentSplashBinding
import com.example.githubapi.util.Constant

class SplashFragment : Fragment() {

    private lateinit var binding : FragmentSplashBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentSplashBinding.inflate(layoutInflater)
        sharedPreferences = requireContext().getSharedPreferences(
            Constant.SHARE_PREFERENCE,
            Context.MODE_PRIVATE)
        (activity as AppCompatActivity).supportActionBar?.hide()
       return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            isLogin()
        },1500)

    }

    private fun isLogin(){
      val id = sharedPreferences.getString(Constant.TOKEN,"undefined")
      if(!id.equals("undefined")){
          Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
      }else{
          Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
      }
    }


}