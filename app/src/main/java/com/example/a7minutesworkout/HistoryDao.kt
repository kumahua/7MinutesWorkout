package com.example.a7minutesworkout

import androidx.room.*
import kotlinx.coroutines.flow.Flow

//定義所有CRUD方法
//data access objects 資料訪問對象
@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    //在@Query(sqlite 語法)
    @Query("SELECT * FROM `history-table`")
    fun fetchAllDates(): Flow<List<HistoryEntity>>
}