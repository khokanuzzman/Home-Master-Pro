package com.example.homemaster.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homemaster.R
import com.example.homemaster.data.ExpanseDataBase
import com.example.homemaster.data.dao.ExpanseDao
import com.example.homemaster.data.model.ExpanseEntity

class HomeViewModel(dao:ExpanseDao): ViewModel() {
    val expanse = dao.getExpanseList()

    fun getBalance(list: List<ExpanseEntity>) :String{
        var total = 0.0
        list.forEach() {
            if (it.type == "Income") {
                total += it.amount
            }else{
                total -= it.amount
            }
        }
        return "$ $total";
    }
    fun getTotalExpanse(list: List<ExpanseEntity>) :String{
        var total = 0.0
        list.forEach() {
            if (it.type == "Expanse") {
                total += it.amount
            }
        }
        return "$ $total";
    }

    fun getTotalIncome(list: List<ExpanseEntity>) :String{
        var total = 0.0
        list.forEach() {
            if (it.type == "Income") {
                total += it.amount
            }
        }
        return "$ $total";
    }

    fun getItemIcon(item: ExpanseEntity): Int {
        if (item.category == "Paypal") {
            return R.drawable.ic_paypal
        } else if (item.category == "Salary") {
            return R.drawable.ic_upwork
        } else if (item.category == "Netflix") {
            return R.drawable.ic_netflix
        } else if (item.category == "Starbucks") {
            return R.drawable.ic_starbucks
        }
        return R.drawable.ic_paypal
    }


}

class HomeViewModelFactory(private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val dao = ExpanseDataBase.getDatabase(context).expanseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}