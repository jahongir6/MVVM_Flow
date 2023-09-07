package com.example.mvvm.repository

import com.example.mvvm.db.dao.UserDao
import com.example.mvvm.db.entity.UserEntity
import com.example.mvvm.models.ApiService
import com.example.mvvm.models.UserData
import kotlinx.coroutines.flow.flow

class UserRepository(var apiService: ApiService,val userDao: UserDao) {
    fun getService() = apiService.getUsers()

    fun addUsers(list: List<UserEntity>) = flow { emit( userDao.addUsers(list))}

    fun getDatabaseUsers() = userDao.getUsers()

    fun getUserCount() = userDao.getUserCount()
}