package com.example.mvvm.mapper

import com.example.mvvm.db.entity.UserEntity
import com.example.mvvm.models.UserData

fun UserData.mapToEntity(userData: UserData): UserEntity {
    return UserEntity(userData.id ?:0,userData.login?:"",userData.avatarUrl?:"")
}