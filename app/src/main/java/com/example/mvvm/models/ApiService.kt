package com.example.mvvm.models

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getUsers():Flow<List<UserData>>
}