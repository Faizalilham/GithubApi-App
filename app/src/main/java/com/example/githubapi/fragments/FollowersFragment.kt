package com.example.githubapi.fragments

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.R
import com.example.githubapi.adapter.UserGithubAdapter
import com.example.githubapi.databinding.FragmentFollowersBinding
import com.example.githubapi.model.UserGithub
import com.example.githubapi.viewmodel.FollowUserViewModel
import com.example.githubapi.viewmodel.UserGithubViewModel


class FollowersFragment : Fragment() {

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
        followUserViewModel.getFollowers(DetailFragment.ARGS)
        followUserViewModel.followers.observe(requireActivity()){
            userGithubAdapter.submitData(it as ArrayList<UserGithub>)
        }

    }

    private fun setRecycler(){
        userGithubAdapter = UserGithubAdapter(object : UserGithubAdapter.Clicked{
            override fun onClicktoDetail(userGithub: UserGithub) {
//               val args = FollowersFragmentDirections.actionFollowersFragmentToDetailFragment2(userGithub)
//               findNavController().navigate(args)
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