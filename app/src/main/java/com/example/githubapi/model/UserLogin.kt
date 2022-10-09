package com.example.githubapi.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class UserLogin(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val username : String,
    val email : String,
    val password : String,
    val name : String,
):Parcelable