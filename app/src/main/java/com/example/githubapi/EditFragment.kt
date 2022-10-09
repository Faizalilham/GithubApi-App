package com.example.githubapi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.githubapi.databinding.FragmentEditBinding
import com.example.githubapi.model.UserLogin
import com.example.githubapi.room.SetupRoom
import com.example.githubapi.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditFragment : Fragment() {


    private lateinit var binding : FragmentEditBinding
    private val args by navArgs<EditFragmentArgs>()
    private val db by lazy{
        SetupRoom(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        editData()
    }

    private fun getData(){
       binding.apply {
           etUsername.setText(args.userLogin.username)
           etName.setText(args.userLogin.name)
           etEmail.setText(args.userLogin.email)
           val information = "username and password to login will not change, you can use username ${args.userLogin.username} and password  ${args.userLogin.password} to login with this account"
           tvInfo.text = information
       }
    }

    private fun editData(){
        binding.btnEditProfile.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            CoroutineScope(Dispatchers.IO).launch {
                db.daoUserLogin().updateUser(
                    UserLogin(
                        args.userLogin.id,
                        username,
                        email,
                        args.userLogin.password,
                        name
                    )
                )
            }
            Navigation.findNavController(binding.root).navigate(R.id.profileFragment3)
        }
    }


}