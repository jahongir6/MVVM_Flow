package com.example.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.db.dao.AppDatabase
import com.example.mvvm.nervorking.ApiClient
import com.example.mvvm.utils.NetvorkHelper
import com.example.mvvm.vm.MyViewModelFactory
import com.example.mvvm.vm.Resource
import com.example.mvvm.vm.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

//MVVM ->model   view   viewModel
//model-> modeld asosan logika boladi,bu yerda malumotlar bazasiga oid bolgan jarayonlar,networking bn bogliq bolgan jarayonlarni koramiz
//view->ui elementlarimiz kiradi
//viewModel->
class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var viewModel: UserViewModel
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this, MyViewModelFactory(
                appDatabase = AppDatabase.getInstance(this), ApiClient.apiService,
                NetvorkHelper(this)
            )
        )[UserViewModel::class.java]

        launch {
            viewModel.getStateFlow()
                .collect() {
                    when (it) {
                        is Resource.Error -> {

                        }
                        is Resource.Success -> {
                            Log.d(TAG, "onCreate: ${it.data}")
                        }
                        is Resource.loading -> {

                        }
                    }
                }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}