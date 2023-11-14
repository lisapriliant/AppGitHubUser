package com.lisapriliant.appgithubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lisapriliant.appgithubuser.R
import com.lisapriliant.appgithubuser.data.database.FavoriteUserEntity
import com.lisapriliant.appgithubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        DetailViewModel.ViewModelFactory.getInstance(application)
    }

    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_user)

        val username = intent.getStringExtra(EXTRA_USER) ?: ""
        val avatar = intent.getStringExtra(EXTRA_AVATAR) ?: ""
        val bundle = Bundle()
        bundle.putString(EXTRA_USER, username)

        detailViewModel.getDetailUser(username)
        showLoading(true)

        detailViewModel.detailUser.observe(this) {
            showLoading(false)
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .centerCrop()
                        .into(civAvatar)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = resources.getString(R.string.total_followers, it.followers)
                    tvFollowing.text = resources.getString(R.string.total_following, it.following)
                }
                binding.tvErrorMessage.visibility = View.GONE
            } else {
                Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show()
                binding.tvErrorMessage.visibility = View.VISIBLE
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.getDataByUsername(username).observe(this){
            isFavorite = it.isNotEmpty()
            val favUser = FavoriteUserEntity(username, avatar)
            if (it.isEmpty()) {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context,
                        R.drawable.favorite_border
                    )
                )
                binding.fabFavorite.contentDescription = getString(R.string.add_favorite)
            } else {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context,
                        R.drawable.favorite_full
                    )
                )
                binding.fabFavorite.contentDescription = getString(R.string.remove_favorite)
            }

            binding.fabFavorite.setOnClickListener {
                if (isFavorite) {
                    detailViewModel.delete(favUser)
                    Toast.makeText(this, R.string.remove_favorite, Toast.LENGTH_SHORT).show()
                } else {
                    detailViewModel.insert(favUser)
                    Toast.makeText(this, R.string.add_favorite, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}