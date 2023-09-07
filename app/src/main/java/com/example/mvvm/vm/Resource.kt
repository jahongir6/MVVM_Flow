package com.example.mvvm.vm

sealed class Resource<T> {

    class loading<T> : Resource<T>()
    class Success<T : Any>(val data: T) : Resource<T>()
    class Error<T : Any>(val e: Throwable) : Resource<T>()
}