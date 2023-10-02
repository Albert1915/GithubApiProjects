package com.alz19.githubapiproject.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alz19.githubapiproject.providers.ApiConfig
import com.alz19.githubapiproject.dataresponse.response.UserItemModel
import com.alz19.githubapiproject.dataresponse.response.UserSearchModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _userList = MutableLiveData<List<UserItemModel>>()
    val userList: LiveData<List<UserItemModel>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun gettingUser() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllUser()
        client.enqueue(object : Callback<List<UserItemModel>> {
            override fun onResponse(call: Call<List<UserItemModel>>, response: Response<List<UserItemModel>>) {
                handleResponse(response)
            }

            override fun onFailure(call: Call<List<UserItemModel>>, t: Throwable) {
                handleError(t)
            }
        })
    }

    private fun handleResponse(response: Response<List<UserItemModel>>) {
        if (response.isSuccessful) {
            _userList.value = response.body()
        } else {
            Log.e(TAG, "onFailure: ${response.message()}")
        }
        _isLoading.value = false
    }

    private fun handleError(t: Throwable) {
        Log.e(TAG, "onFailure: ${t.message.toString()}")
        _isLoading.value = false
    }


    fun gettingSearchUser(params: String) {
        _isLoading.value = true
        _userList.value = emptyList()

        val client = ApiConfig.getApiService().getSearchUser(params)
        client.enqueue(object : Callback<UserSearchModel> {
            override fun onResponse(call: Call<UserSearchModel>, response: Response<UserSearchModel>) {
                handleSearchResponse(response)
            }

            override fun onFailure(call: Call<UserSearchModel>, t: Throwable) {
                handleSearchError(t)
            }
        })
    }

    private fun handleSearchResponse(response: Response<UserSearchModel>) {
        if (!response.isSuccessful) {
            Log.e(TAG, "onFailure: ${response.message()}")
        }
        val userList = response.body()?.items ?: emptyList()
        _userList.value = userList
        _isLoading.value = false
    }

    private fun handleSearchError(t: Throwable) {
        Log.e(TAG, "onFailure: ${t.message.toString()}")
        _isLoading.value = false
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}