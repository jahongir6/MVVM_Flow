package com.example.mvvm.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvm.db.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    fun addUser(userEntity: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(list: List<UserEntity>)

    @Query("select * from userentity")
    fun getUsers(): List<UserEntity>

    @Query("select count(*) from userentity")
    fun getUserCount():Int
}