package com.lisapriliant.appgithubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.lisapriliant.appgithubuser.data.database.FavoriteUserDao
import com.lisapriliant.appgithubuser.data.database.FavoriteUserEntity
import com.lisapriliant.appgithubuser.data.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>> = mFavoriteUserDao.getAllFavoriteUser()

    fun insert(favoriteUser: FavoriteUserEntity) {
        executorService.execute {
            mFavoriteUserDao.insert(favoriteUser)
        }
    }

    fun delete(favoriteUser: FavoriteUserEntity) {
        executorService.execute {
            mFavoriteUserDao.delete(favoriteUser)
        }
    }

    fun getDataByUsername(username: String): LiveData<List<FavoriteUserEntity>> = mFavoriteUserDao.getDataByUsername(username)
}