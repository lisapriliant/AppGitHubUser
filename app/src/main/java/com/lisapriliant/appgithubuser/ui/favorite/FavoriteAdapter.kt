package com.lisapriliant.appgithubuser.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lisapriliant.appgithubuser.data.database.FavoriteUserEntity
import com.lisapriliant.appgithubuser.databinding.ItemUsersBinding
import de.hdodenhof.circleimageview.CircleImageView

class FavoriteAdapter(private val listFavUsers: List<FavoriteUserEntity>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFavUsers[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUserEntity)
    }

    inner class ViewHolder(binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        private val tvUser: TextView = binding.tvUsername
        private val civPhoto: CircleImageView = binding.civAvatar

        fun bind(favUsers: FavoriteUserEntity) {
            tvUser.text = favUsers.username
            Glide.with(itemView.context)
                .load(favUsers.avatarUrl)
                .into(civPhoto)
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(favUsers)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}