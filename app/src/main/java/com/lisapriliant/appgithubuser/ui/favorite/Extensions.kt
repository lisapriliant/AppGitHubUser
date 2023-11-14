package com.lisapriliant.appgithubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lisapriliant.appgithubuser.ui.detail.DetailActivity

fun AppCompatActivity.obtainViewModel(): FavoriteUserViewModel {
    val factory = FavoriteUserViewModel.ViewModelFactory.getInstance(application)
    return ViewModelProvider(this, factory)[FavoriteUserViewModel::class.java]
}

fun AppCompatActivity.startActivity(username: String, avatarUrl: String?) {
    startActivity(
        Intent(this, DetailActivity::class.java)
            .putExtra(DetailActivity.EXTRA_USER, username)
            .putExtra(DetailActivity.EXTRA_AVATAR, avatarUrl)
    )
}