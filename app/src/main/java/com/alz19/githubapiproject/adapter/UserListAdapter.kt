
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alz19.githubapiproject.R
import com.alz19.githubapiproject.dataresponse.response.UserItemModel
import com.alz19.githubapiproject.views.activity.DetailActivity
import com.bumptech.glide.Glide

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private var userList: List<UserItemModel?> = emptyList()

    fun setUserList(value: List<UserItemModel>) {
        val diffResult = DiffUtil.calculateDiff(UserListDiffCallback(userList, value))
        userList = value
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val githubIdItem: TextView = itemView.findViewById(R.id.githubIdItemTv)
        val avatarItem: ImageView = itemView.findViewById(R.id.avatarItemIv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserListViewHolder(view)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = userList[position]

        holder.githubIdItem.text = user?.githubId.orEmpty()

        Glide.with(holder.itemView.context)
            .load(user?.avatarUrl ?: "https://i.stack.imgur.com/l60Hf.png")
            .into(holder.avatarItem)

        holder.itemView.setOnClickListener {
            user?.githubId?.let { username ->
                val moveIntent = Intent(holder.itemView.context, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_USERNAME, username)
                holder.itemView.context.startActivity(moveIntent)
            }
        }
    }

    private class UserListDiffCallback(
        private val oldList: List<UserItemModel?>,
        private val newList: List<UserItemModel>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition]?.id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            // Add bundle with changes if needed
            val diffBundle = Bundle()

            if (oldItem?.githubId != newItem.githubId) {
                diffBundle.putString("username", newItem.githubId)
            }

            return if (diffBundle.size() == 0) null else diffBundle
        }
    }
}
