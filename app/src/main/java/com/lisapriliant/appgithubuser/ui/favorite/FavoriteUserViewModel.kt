package com.lisapriliant.appgithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lisapriliant.appgithubuser.data.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavoriteUser() = favoriteUserRepository.getAllFavoriteUser()

    class ViewModelFactory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {
        companion object {
            @Volatile
            private var INSTANCE: ViewModelFactory? = null

            @JvmStatic
            fun getInstance(application: Application) : ViewModelFactory {
                if (INSTANCE == null) {
                    synchronized(ViewModelFactory::class.java) {
                        INSTANCE = ViewModelFactory(application)
                    }
                }
                return INSTANCE as ViewModelFactory
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
                return FavoriteUserViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}