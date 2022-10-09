package com.example.githubapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class AuthUser(
    var id : Int,
    var name : String,
    var username : String,
    var email : String,
    var password : String,
    var updatedAt : Date,
    var createdAt : Date,
    var token : String,
):Parcelable
