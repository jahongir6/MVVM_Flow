package com.example.mvvm.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.db.dao.AppDatabase
import com.example.mvvm.db.entity.UserEntity
import com.example.mvvm.mapper.mapToEntity
import com.example.mvvm.models.ApiService
import com.example.mvvm.models.UserData
import com.example.mvvm.nervorking.ApiClient
import com.example.mvvm.repository.UserRepository
import com.example.mvvm.utils.NetvorkHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserViewModel(
    private val appDatabase: AppDatabase,
    private val apiService: ApiService,
    private val netvorkHelper: NetvorkHelper
) : ViewModel() {
    private var userRepository =
        UserRepository(apiService = apiService, appDatabase.userDao())
    private var stateFlow = MutableStateFlow<Resource<List<UserEntity>>>(Resource.loading())

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            if (netvorkHelper.isNetworkConnected()) {
                userRepository.getService()
                    .catch {
                        stateFlow.emit(Resource.Error(it))
                    }
                    .flatMapConcat {
                        val list = ArrayList<UserEntity>()
                        it.forEach {
                            list.add(it.mapToEntity(it))
                        }
                        userRepository.addUsers(list)
                    }
                    .collect {
                        stateFlow.emit(Resource.Success(userRepository.getDatabaseUsers()))
                    }
            } else {
                if (userRepository.getUserCount() > 0) {
                    stateFlow.emit(Resource.Success(userRepository.getDatabaseUsers()))
                } else
                    stateFlow.emit(Resource.Error(Throwable("internet not connection")))
            }
        }
    }

    fun getStateFlow(): StateFlow<Resource<List<UserEntity>>> {
        return stateFlow
    }
}