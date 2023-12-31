package com.alz19.githubapiproject.views.fragment

import UserListAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alz19.githubapiproject.databinding.FragmentFollowBinding
import com.alz19.githubapiproject.dataresponse.response.UserItemModel
import com.alz19.githubapiproject.view_models.FollowViewModel

class FollowFragment : Fragment() {

    private var position: Int = 0
    private var username: String = ""

    private lateinit var fragmentFollowBinding: FragmentFollowBinding
    private lateinit var followViewModel: FollowViewModel
    private lateinit var adapter: UserListAdapter

    private fun initComponent() {
        fragmentFollowBinding = FragmentFollowBinding.inflate(layoutInflater)
        followViewModel = ViewModelProvider(this)[FollowViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(EXTRA_POSITION)
            username = it.getString(EXTRA_USERNAME) ?: ""
        }

        with(fragmentFollowBinding) {
            followRv.layoutManager = LinearLayoutManager(requireActivity())
            adapter = UserListAdapter()
            followRv.adapter = adapter
        }

        if (position == 0){
            followViewModel.gettingFollowData(username, 0)
        } else {
            followViewModel.gettingFollowData(username, 1)
        }

        followViewModel.followList.observe(requireActivity()) {
            setUserListData(it)
        }

        followViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initComponent()
        return fragmentFollowBinding.root
    }

    private fun setUserListData(userList: List<UserItemModel>) {
        adapter.setUserList(userList)
        fragmentFollowBinding.followRv.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        fragmentFollowBinding.followPB.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "username"
        const val EXTRA_POSITION = "position"
    }
}