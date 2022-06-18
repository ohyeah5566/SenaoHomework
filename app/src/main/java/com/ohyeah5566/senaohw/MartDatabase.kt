package com.ohyeah5566.senaohw

import android.content.Context
import androidx.room.*

@Dao
interface MartDao {
    @Query("select * from mart")
    suspend fun getAllMart(): List<Mart>

    @Query("select * from mart where mart.martName like :keyword ")
    suspend fun searchMart(keyword: String): List<Mart>

    @Insert
    suspend fun insertAll(marts: List<Mart>)

    @Query("DELETE FROM mart")
    suspend fun clearALl()
}

@Database(entities = [Mart::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun martDao(): MartDao
}

fun getDb(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "senao_mart_db"
    ).build()
}
