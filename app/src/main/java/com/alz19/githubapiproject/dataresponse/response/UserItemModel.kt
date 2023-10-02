package com.alz19.githubapiproject.dataresponse.response

import com.google.gson.annotations.SerializedName

data class UserItemModel(

    @field:SerializedName("login")
    val githubId: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    val username: String? = null,

    val id: String? = null
    )
