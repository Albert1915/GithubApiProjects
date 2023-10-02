package com.alz19.githubapiproject.view_models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alz19.githubapiproject.dataresponse.response.UserItemModel
import com.alz19.githubapiproject.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val _userFavoriteList = MutableLiveData<List<UserItemModel>>()
    val userFavoriteList: LiveData<List<UserItemModel>> = _userFavoriteList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun gettingUserFavorite() {
        _isLoading.value = true

        favoriteUserRepository.getAllFavoriteUser().observeForever { favorites ->
            val userItemModels = favorites.map { favoriteUserModel ->
                UserItemModel(favoriteUserModel.username, favoriteUserModel.avatarUrl)
            }


            _userFavoriteList.postValue(userItemModels)
            _isLoading.postValue(false)
        }
    }
    companion object {
        private const val TAG = "FavoriteViewModel"
    }
}