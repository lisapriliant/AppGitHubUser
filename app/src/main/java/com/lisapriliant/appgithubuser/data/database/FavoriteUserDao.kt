package com.lisapriliant.appgithubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUserEntity)

    @Delete
    fun delete(favoriteUser: FavoriteUserEntity)

    @Query("SELECT * FROM FavoriteUser ORDER BY username ASC")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getDataByUsername(username: String): LiveData<List<FavoriteUserEntity>>
}