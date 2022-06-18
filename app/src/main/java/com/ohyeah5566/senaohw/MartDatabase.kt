package com.ohyeah5566.senaohw

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface MartDao {
    @Query("select * from mart")
    suspend fun getAllMart(): List<Mart>

    @Query("select * from mart where mart.martName like :keyword ")
    suspend fun searchMart(keyword: String): List<Mart>

    @Query("select * from mart where mart.martName like '%'||:keyword||'%' ")
    fun searchMartPagingSource(keyword: String): PagingSource<Int, Mart>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(marts: List<Mart>)

    @Query("DELETE FROM mart")
    suspend fun clearAll()
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
