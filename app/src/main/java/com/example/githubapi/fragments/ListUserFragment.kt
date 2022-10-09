package com.example.githubapi.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.R
import com.example.githubapi.adapter.UserGithubAdapter
import com.example.githubapi.databinding.FragmentListUserBinding
import com.example.githubapi.model.UserGithub
import com.example.githubapi.model.UserLogin
import com.example.githubapi.room.SetupRoom
import com.example.githubapi.util.Constant
import com.example.githubapi.viewmodel.UserGithubViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.*


class ListUserFragment : Fragment() {


    private lateinit var binding : FragmentListUserBinding
    private lateinit var userGithubAdapter : UserGithubAdapter
    private lateinit var userGithubViewModel: UserGithubViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private val db by lazy{
        SetupRoom(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentListUserBinding.inflate(layoutInflater)
        userGithubViewModel = ViewModelProvider(requireActivity())[UserGithubViewModel::class.java]
        sharedPreferences = requireContext().getSharedPreferences(
            Constant.SHARE_PREFERENCE,
            Context.MODE_PRIVATE)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
        showLoading(true)
        userGithubViewModel.searchUser("Faizal")
        keyEventSearch()
        userGithubViewModel.datas.observe(requireActivity()){
            userGithubAdapter.submitData(it)
            showLoading(false)
        }
        setupUsersLogin()
        binding.btnVoice.setOnClickListener {
            askToSpeech()
        }

    }

    private fun searchUserGithub(search : String){
            if(search.isNotBlank()){
                userGithubViewModel.searchUser(search)
                userGithubViewModel.datas.observe(requireActivity()){
                    if(it != null){
                        if(it.size > 0){
                            userGithubAdapter.submitData(it)
                            binding.imageSearchResult.setImageDrawable(null)
                            showLoading(false)
                        }else{
                            binding.imageSearchResult.setImageResource(R.drawable.not_found)
                            showLoading(false)
                        }
                    }else{
                        binding.imageSearchResult.setImageResource(R.drawable.not_found)
                        showLoading(false)
                    }
                }
            }else{
                setToast("Warning !","Field search cannot be empety",MotionToastStyle.WARNING)
            }
    }

    private fun setRecycler(){
        userGithubAdapter = UserGithubAdapter(object : UserGithubAdapter.Clicked{
            override fun onClicktoDetail(userGithub: UserGithub) {
                val args = ListUserFragmentDirections.actionListUserFragmentToDetailFragment(userGithub)
                findNavController().navigate(args)
            }

            override fun onClicktoSearch(userGithub: UserGithub) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(userGithub.html_url)
                }
                startActivity(intent)
            }
        })
        binding.recyclerListUser.apply {
            adapter = userGithubAdapter
            layoutManager = if(context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                GridLayoutManager(requireActivity(),2)
            }else{
                LinearLayoutManager(requireActivity())
            }
        }
    }

    private fun askToSpeech(){
        if(!SpeechRecognizer.isRecognitionAvailable(requireActivity())){
            setToast("Error","Cannot use speechRecognizer",MotionToastStyle.ERROR)
        }else{
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Anything")
            }
            startActivityForResult(i,201)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 201 && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
           if(result != null){
               searchUserGithub(result[0].toString())
               binding.etSearch.setText(result[0].toString())
           }
        }
    }

    private fun showLoading(isLoading : Boolean){
        if (isLoading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
    }

    private fun setToast(tittle : String,message :String,style : MotionToastStyle){
        MotionToast.createToast(requireActivity(), tittle,
            message,
            style,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(requireActivity(),R.font.poppins_regular))
    }

    private fun keyEventSearch(){
        binding.etSearch.setOnEditorActionListener { _, actionId,_ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val search = binding.etSearch.text.toString()
                searchUserGithub(search)
                true
            } else false
        }
    }

    private fun setupUsersLogin(){
        val id = sharedPreferences.getInt(Constant.ID_USER,0)
        val name = sharedPreferences.getString(Constant.NAME,"undefined")
        val username = sharedPreferences.getString(Constant.USERNAME,"undefined")
        val email = sharedPreferences.getString(Constant.EMAIL,"undefined")
        val password = sharedPreferences.getString(Constant.PASSWORD,"undefined")
        Log.d("JUMLAHHH", id.toString())
        if(id != 0){
            CoroutineScope(Dispatchers.IO).launch {
                val cekId = db.daoUserLogin().checkUserLogin(id)
                if(cekId > 0){
                    Log.d("JUMLAH", cekId.toString())
                }else{
                    if(name != null && username != null && email != null && password != null){
                        insertNewUser(id,username,email,password,name)
                    }
                    Log.d("JUMLAH", cekId.toString())
                }
            }
        }else{
            Toast.makeText(requireActivity(), "$id", Toast.LENGTH_SHORT).show()
        }
    }

    private fun insertNewUser(id:Int,username:String,email:String,password:String,name:String){
        CoroutineScope(Dispatchers.IO).launch {
            db.daoUserLogin().insertUser(
                UserLogin(
                    id,
                    username,email,password,name
                )
            )
        }
    }







}