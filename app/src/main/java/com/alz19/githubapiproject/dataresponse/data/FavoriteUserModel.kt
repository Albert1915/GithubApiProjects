package com.alz19.githubapiproject.dataresponse.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteUserModel(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = null,
)