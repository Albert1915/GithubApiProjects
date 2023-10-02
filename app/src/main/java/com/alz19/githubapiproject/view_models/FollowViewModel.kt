package com.alz19.githubapiproject.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alz19.githubapiproject.providers.ApiConfig
import com.alz19.githubapiproject.dataresponse.response.UserItemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _followList = MutableLiveData<List<UserItemModel>>()
    val followList: LiveData<List<UserItemModel>> = _followList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun gettingFollowData(username: String, position: Int) {
        _isLoading.value = true

        val client = when (position) {
            0 -> ApiConfig.getApiService().getUserFollower(username)
            else -> ApiConfig.getApiService().getUserFollowing(username)
        }

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
            _followList.value = response.body()
        } else {
            Log.e(TAG, "onFailure: ${response.message()}")
        }
        _isLoading.value = false
    }

    private fun handleError(t: Throwable) {
        Log.e(TAG, "onFailure: ${t.message.toString()}")
        _isLoading.value = false
    }

    companion object {
        private const val TAG = "FollowerViewModel"
    }
}