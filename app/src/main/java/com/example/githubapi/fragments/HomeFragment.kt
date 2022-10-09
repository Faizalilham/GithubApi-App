package com.example.githubapi.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.githubapi.R
import com.example.githubapi.adapter.UserGithubAdapter
import com.example.githubapi.databinding.FragmentHomeBinding
import com.example.githubapi.model.AuthUser
import com.example.githubapi.model.UserGithub
import com.example.githubapi.model.UserLogin
import com.example.githubapi.room.SetupRoom
import com.example.githubapi.viewmodel.UserGithubViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {


    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val host = childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(host.navController)
        binding.bottomNavigation.background = null
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabFavorite.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.bookmarkFragment)
        }

    }


}