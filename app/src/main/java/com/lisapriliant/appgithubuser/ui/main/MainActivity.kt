package com.lisapriliant.appgithubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lisapriliant.appgithubuser.R
import com.lisapriliant.appgithubuser.data.response.ItemsItem
import com.lisapriliant.appgithubuser.databinding.ActivityMainBinding
import com.lisapriliant.appgithubuser.ui.detail.DetailActivity
import com.lisapriliant.appgithubuser.ui.favorite.FavoriteUserActivity
import com.lisapriliant.appgithubuser.ui.mode.ModeSettingActivity
import com.lisapriliant.appgithubuser.ui.mode.SettingPreferences
import com.lisapriliant.appgithubuser.ui.mode.datastore
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val query = binding.searchView.text.toString()
                    mainViewModel.searchUsers(query)
                    searchBar.text = searchView.text
                    binding.searchView.hide()
                    true
                }

            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.favUsers -> {
                        val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.mode_setting -> {
                        val intent = Intent(this@MainActivity, ModeSettingActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }

        val settingPreferences = SettingPreferences.getInstance(datastore)

        lifecycleScope.launch {
            settingPreferences.getModeSetting().collect { isDarkModeActive ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        mainAdapter = MainAdapter()
        mainAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USER, data.login)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                    it.putExtra(Intent.EXTRA_TITLE, data.login)
                    startActivity(it)
                }
            }
        })

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.listUsers.observe(this) { list ->
            if (list.isNotEmpty()) {
                mainAdapter.setList(list)
                binding.rvListUsers.visibility = View.VISIBLE
                binding.tvErrorMessage.visibility = View.GONE
            } else {
                binding.rvListUsers.visibility = View.GONE
                binding.tvErrorMessage.visibility = View.VISIBLE
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvListUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListUsers.addItemDecoration(itemDecoration)

        binding.apply {
            rvListUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvListUsers.setHasFixedSize(true)
            rvListUsers.adapter = mainAdapter
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onResume() {
        super.onResume()
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            binding.searchBar.menu.findItem(R.id.favUsers)?.setIcon(R.drawable.favorite_full)
            binding.searchBar.menu.findItem(R.id.mode_setting)?.setIcon(R.drawable.sunny)
        } else {
            binding.searchBar.menu.findItem(R.id.favUsers)?.setIcon(R.drawable.favorite_full_dark)
            binding.searchBar.menu.findItem(R.id.mode_setting)?.setIcon(R.drawable.nights_stay)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }
}