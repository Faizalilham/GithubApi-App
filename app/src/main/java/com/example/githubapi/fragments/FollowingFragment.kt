package com.example.githubapi.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.adapter.UserGithubAdapter
import com.example.githubapi.databinding.FragmentFollowersBinding
import com.example.githubapi.model.UserGithub
import com.example.githubapi.viewmodel.FollowUserViewModel

class FollowingFragment : Fragment() {

    private lateinit var binding : FragmentFollowersBinding
    private lateinit var userGithubAdapter: UserGithubAdapter
    private lateinit var followUserViewModel: FollowUserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = FragmentFollowersBinding.inflate(layoutInflater)
        followUserViewModel = ViewModelProvider(requireActivity())[FollowUserViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
        followUserViewModel.getFollowing(DetailFragment.ARGS)
        followUserViewModel.following.observe(requireActivity()){
            userGithubAdapter.submitData(it as ArrayList<UserGithub>)
        }
    }



    private fun setRecycler(){
        userGithubAdapter = UserGithubAdapter(object : UserGithubAdapter.Clicked{
            override fun onClicktoDetail(userGithub: UserGithub) {
                Log.d("Pusing",userGithub.login)
            }

            override fun onClicktoSearch(userGithub: UserGithub) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(userGithub.html_url)
                }
                startActivity(intent)
            }
        })
        binding.recyclerViewPager.apply {
            adapter = userGithubAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }
}