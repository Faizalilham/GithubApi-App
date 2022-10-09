package com.example.githubapi.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.githubapi.R
import com.example.githubapi.databinding.FragmentProfileBinding
import com.example.githubapi.model.UserLogin
import com.example.githubapi.room.SetupRoom
import com.example.githubapi.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val db by lazy{
        SetupRoom(requireActivity())
    }
    private val listUser : MutableList<UserLogin> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      binding = FragmentProfileBinding.inflate(layoutInflater)
      return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(Constant.SHARE_PREFERENCE,Context.MODE_PRIVATE)
        getUser()
        binding.btnLanguage.setOnClickListener {
            setLanguage("ja")
        }

    }

    private fun setLanguage(lang : String){
        val myLocale = Locale(lang)
        val conf = resources.configuration
        conf.locale = myLocale
        resources.updateConfiguration(conf,resources.displayMetrics)
        Navigation.findNavController(binding.root).navigate(R.id.profileFragment3)


    }

    private fun getUser(){
        val id = sharedPreferences.getInt(Constant.ID_USER,0)
        CoroutineScope(Dispatchers.IO).launch {
            val user = db.daoUserLogin().getUserById(id)
            withContext(Dispatchers.Main){
                listUser.addAll(user)
                binding.apply {
                    if(listUser.size > 0){
                        val username = user[0].username
                        val name = listUser[0].name
                        val email = listUser[0].email
                        val id = listUser[0].id
                        val password = listUser[0].password
                        tvUsername.text = username
                        tvName.text = name
                        tvEmail.text = email
                        val userLogin = UserLogin(id,username,email,password,name)

                        btnEditProfile.setOnClickListener {
                            val args = ProfileFragmentDirections.actionProfileFragment3ToEditFragment(userLogin)
                            findNavController().navigate(args)
                        }

                    }
                }
            }
        }
        Log.d("ID_USERRRRR", id.toString())

        binding.apply {
            btnLogout.setOnClickListener {
                sharedPreferences.edit().apply {
                    clear()
                    apply()
                }
                Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment3_to_loginFragment2)
            }

        }
    }

}