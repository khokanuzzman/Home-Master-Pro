package com.example.homemaster.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homemaster.data.ExpanseDataBase
import com.example.homemaster.data.dao.ExpanseDao
import com.example.homemaster.data.model.ExpanseEntity

class AddExpViewModel(val dao: ExpanseDao):ViewModel() {

    suspend fun insertExpanse(expanseEntity: ExpanseEntity):Boolean {
        return  try {
         dao.insertExpanse(expanseEntity)
         true
        }catch (e:Exception){
            false
        }
    }
}

class AddExpViewModelFactory(private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpViewModel::class.java)) {
            val dao = ExpanseDataBase.getDatabase(context).expanseDao()
            @Suppress("UNCHECKED_CAST")
            return AddExpViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}