package com.lisapriliant.appgithubuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lisapriliant.appgithubuser.data.response.ItemsItem
import com.lisapriliant.appgithubuser.databinding.ItemUsersBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private val listUsers = ArrayList<ItemsItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    inner class ViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .centerCrop()
                    .into(civAvatar)
                tvUsername.text = user.login
                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(newList: List<ItemsItem>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(listUsers, newList))
        listUsers.clear()
        listUsers.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class DiffCallback(
        private val oldList: List<ItemsItem>,
        private val newList: List<ItemsItem>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
    }
}
