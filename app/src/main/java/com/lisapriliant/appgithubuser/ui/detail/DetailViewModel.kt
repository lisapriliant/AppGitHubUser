package com.lisapriliant.appgithubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lisapriliant.appgithubuser.data.database.FavoriteUserEntity
import com.lisapriliant.appgithubuser.data.repository.FavoriteUserRepository
import com.lisapriliant.appgithubuser.data.response.DetailUserResponse
import com.lisapriliant.appgithubuser.data.response.ItemsItem
import com.lisapriliant.appgithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val errorMessage = MutableLiveData<String>()

    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(favoriteUser: FavoriteUserEntity) {
        favoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUserEntity) {
        favoriteUserRepository.delete(favoriteUser)
    }

    fun getDataByUsername(username: String) = favoriteUserRepository.getDataByUsername(username)

    init {
        getDetailUser()
    }

    fun getDetailUser(username: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                errorMessage.postValue(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowers(username: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    class ViewModelFactory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {
        companion object {
            @Volatile
            private var INSTANCE: ViewModelFactory? = null

            @JvmStatic
            fun getInstance(application: Application): ViewModelFactory {
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
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}