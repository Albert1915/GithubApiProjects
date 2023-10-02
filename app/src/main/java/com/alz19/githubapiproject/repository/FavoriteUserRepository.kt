package com.alz19.githubapiproject.repository

import android.app.Application
import com.alz19.githubapiproject.database.FavoriteUserDatabase
import com.alz19.githubapiproject.dataresponse.dao.FavoriteUserDao
import com.alz19.githubapiproject.dataresponse.data.FavoriteUserModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val favoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        favoriteUserDao = db.favoriteUserDao()
    }


    fun insertFavoriteUser(favoriteUser: FavoriteUserModel) {
        executorService.execute { favoriteUserDao.insertFavoriteUser(favoriteUser) }
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUserModel) {
        executorService.execute { favoriteUserDao.deleteFavoriteUser(favoriteUser) }
    }

    fun getFavoriteUserByUsername(username: String) = favoriteUserDao.getFavoriteUserByUsername(username)


    fun getAllFavoriteUser() = favoriteUserDao.getAllFavoriteUser()


}