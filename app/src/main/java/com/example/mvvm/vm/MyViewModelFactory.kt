package com.example.mvvm.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.db.dao.AppDatabase
import com.example.mvvm.models.ApiService
import com.example.mvvm.utils.NetvorkHelper


class MyViewModelFactory(
    private val appDatabase: AppDatabase,
    private val apiService: ApiService,
    private val netvorkHelper: NetvorkHelper
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(appDatabase,apiService,netvorkHelper) as T
        }
        throw IllegalArgumentException("Error")
    }
}