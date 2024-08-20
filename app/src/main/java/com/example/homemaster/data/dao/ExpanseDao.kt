package com.example.homemaster.data.dao

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.homemaster.data.ExpanseDataBase
import com.example.homemaster.data.model.ExpanseEntity
import com.example.homemaster.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpanseDao {

    @Query("SELECT * FROM expanse_table")
    fun getExpanseList(): Flow<List<ExpanseEntity>>

    @Insert
    suspend fun insertExpanse(expanseEntity: ExpanseEntity)

    @Delete
    suspend fun deleteExpanse(expanseEntity: ExpanseEntity)

    @Update
    suspend fun updateExpanse(expanseEntity: ExpanseEntity)

}