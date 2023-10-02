package com.alz19.githubapiproject.views.activity

import UserListAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alz19.githubapiproject.databinding.ActivityFavoriteBinding
import com.alz19.githubapiproject.dataresponse.response.UserItemModel
import com.alz19.githubapiproject.helper.ViewModelFactory
import com.alz19.githubapiproject.view_models.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var activityFavoriteBinding: ActivityFavoriteBinding
    private lateinit var adapter: UserListAdapter
    private lateinit var favoriteList: List<UserItemModel>



    private fun initComponent() {
        activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        favoriteViewModel = obtainViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityFavoriteBinding.root)
        supportActionBar?.hide()

        with(activityFavoriteBinding) {
            favoriteUserListRv.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = UserListAdapter()
            favoriteUserListRv.adapter
        }

        favoriteViewModel.userFavoriteList.observe(this) { favorites ->
            val temp = mutableListOf<UserItemModel>()
            for (item in favorites) {
                temp.add(UserItemModel(item.username, item.avatarUrl))
            }
            favoriteList = temp
            setUserFavoriteList(favoriteList)
        }

        favoriteViewModel.gettingUserFavorite()

        favoriteViewModel.userFavoriteList.observe(this) {
            setUserFavoriteList(it)
        }
        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun setUserFavoriteList(userList: List<UserItemModel>) {
        adapter.setUserList(userList)
        activityFavoriteBinding.favoriteUserListRv.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        activityFavoriteBinding.favoritePB.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.gettingUserFavorite()
        showLoading(true)
    }

    override fun onPause() {
        super.onPause()
        showLoading(false)
    }
}