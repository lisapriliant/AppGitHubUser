package com.lisapriliant.appgithubuser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.lisapriliant.appgithubuser.R
import com.lisapriliant.appgithubuser.data.database.FavoriteUserEntity
import com.lisapriliant.appgithubuser.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite)

        favoriteUserViewModel = obtainViewModel()

        favoriteUserViewModel.getAllFavoriteUser().observe(this) {
            setFavoriteUsers(it)
        }

        favoriteUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setFavoriteUsers(favoriteUserEntities: List<FavoriteUserEntity>) {
        val items = arrayListOf<FavoriteUserEntity>()
        favoriteUserEntities.map {
            val item = FavoriteUserEntity(
                username = it.username,
                avatarUrl = it.avatarUrl
            )
            items.add(item)
        }

        val adapter = FavoriteAdapter(items)
        binding.rvFavoriteUser.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUser.setHasFixedSize(true)
        binding.rvFavoriteUser.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteUserEntity) {
                startActivity(data.username, data.avatarUrl)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }
}