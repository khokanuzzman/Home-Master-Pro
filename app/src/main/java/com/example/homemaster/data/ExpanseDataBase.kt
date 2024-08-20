package com.example.homemaster.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.homemaster.Utils
import com.example.homemaster.data.dao.ExpanseDao
import com.example.homemaster.data.model.ExpanseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ExpanseEntity::class], version = 1)
abstract class ExpanseDataBase:RoomDatabase(){
    abstract fun expanseDao(): ExpanseDao
    companion object {
        const val DATABASE_NAME = "ExpanseDataBase"
        @JvmStatic
        fun getDatabase(context: android.content.Context): ExpanseDataBase {
            return Room.databaseBuilder(
                context,
                ExpanseDataBase::class.java,
                DATABASE_NAME
            ).addCallback(object : Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    initBasicData(context)
                }

                fun initBasicData(context: Context){
                    val db = getDatabase(context).expanseDao()
                    CoroutineScope(Dispatchers.IO).launch {
                        db.insertExpanse(ExpanseEntity(1, "Salary", 5000.40, Utils.convertMillisToDate(System.currentTimeMillis()), "Salary", "Income"))
                        db.insertExpanse(ExpanseEntity(2, "Paypal", 5200.40, Utils.convertMillisToDate(System.currentTimeMillis()), "Paypal", "Income"))
                        db.insertExpanse(ExpanseEntity(3, "Netflix", 210.40, Utils.convertMillisToDate(System.currentTimeMillis()), "Netflix", "Expanse"))
                        db.insertExpanse(ExpanseEntity(4, "Starbucks", 210.40, Utils.convertMillisToDate(System.currentTimeMillis()), "Starbucks", "Expanse"))
                    }
                }
            })
                .build()
        }

    }

}