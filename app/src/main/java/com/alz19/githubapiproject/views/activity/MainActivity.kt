package com.alz19.githubapiproject.views.activity

import UserListAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alz19.githubapiproject.R
import com.alz19.githubapiproject.databinding.ActivityMainBinding
import com.alz19.githubapiproject.dataresponse.response.UserItemModel
import com.alz19.githubapiproject.helper.SettingPreferences
import com.alz19.githubapiproject.helper.ViewModelFactory
import com.alz19.githubapiproject.helper.dataStore
import com.alz19.githubapiproject.view_models.MainViewModel
import com.alz19.githubapiproject.view_models.SettingViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var adapter: UserListAdapter

    private fun initComponent() {
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityMainBinding.root)

        with(activityMainBinding) {

            userListRv.layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UserListAdapter()
            userListRv.adapter = adapter

            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                searchBar.text = searchView.text
                searchView.hide()
                if (searchView.text?.isEmpty() == true) {
                    mainViewModel.gettingUser()
                } else {
                    mainViewModel.gettingSearchUser(searchView.text.toString())
                }
                false
            }

            searchBar.inflateMenu(R.menu.favorite_menu)
            searchBar.setOnMenuItemClickListener {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
        }



        val pref = SettingPreferences.getInstance(application.dataStore)

        val settingViewModel =
            ViewModelProvider(this, ViewModelFactory.ViewModelFactorySettingPreferences(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel.gettingUser()

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.userList.observe(this) {
            setUserListData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favoritePage -> {
                startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
                true
            }
            R.id.settingPage -> {
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




    private fun setUserListData(userList: List<UserItemModel>) {
        adapter.setUserList(userList)
        activityMainBinding.userListRv.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        activityMainBinding.mainPB.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}