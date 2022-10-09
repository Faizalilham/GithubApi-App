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
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.R
import com.example.githubapi.adapter.FavoriteAdapter
import com.example.githubapi.adapter.UserGithubAdapter
import com.example.githubapi.databinding.FragmentBookmarkBinding
import com.example.githubapi.model.Favorite
import com.example.githubapi.model.UserGithub
import com.example.githubapi.room.SetupRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BookmarkFragment : Fragment() {

    private lateinit var binding : FragmentBookmarkBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val db by lazy{
        SetupRoom(requireActivity())
    }
    private val listData : MutableList<Favorite> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentBookmarkBinding.inflate(layoutInflater)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
        setDataToRecycler()
    }

    private fun setDataToRecycler(){
        CoroutineScope(Dispatchers.IO).launch {
            val datax = db.daoFavorite().getAllFavorite()
            withContext(Dispatchers.Main){
                listData.addAll(datax)
                favoriteAdapter.submitData(listData)
            }
        }
    }

    private fun setRecycler(){
        favoriteAdapter = FavoriteAdapter(object : FavoriteAdapter.Clicked{
            override fun onClicktoDetail(favorite: Favorite) {
               // Pusying
               Log.d("Pusying","Ternyata pake navcomp ada minusnya")
            }

            override fun onClicktoSearch(favorite: Favorite) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(favorite.html_url)
                }
                startActivity(intent)
            }
        })
        binding.recyclerFavorite.apply {
            adapter = favoriteAdapter
            layoutManager = if(context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                GridLayoutManager(requireActivity(),2)
            }else{
                LinearLayoutManager(requireActivity())
            }
        }
    }

    companion object{
        const val DATA_DETAIL_FAVORITE = "data_detail_favorite"
    }


}