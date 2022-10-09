package com.example.githubapi.room


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.githubapi.model.UserLogin

@Dao
interface DaoUserLogin {

    @Query("SELECT * FROM UserLogin WHERE email = :email AND password = :password ")
    fun getUserLogin(email :String,password : String):MutableList<UserLogin>

    @Query("SELECT * FROM UserLogin WHERE id = :id")
    fun getUserById(id : Int):MutableList<UserLogin>

    @Query("SELECT COUNT(*) FROM UserLogin WHERE id = :id")
    suspend fun checkUserLogin(id :Int):Int

    @Insert
    suspend fun insertUser(userLogin: UserLogin)

    @Update
    suspend fun updateUser(userLogin: UserLogin)


}