package com.example.a7minutesworkout

import androidx.room.Entity
import androidx.room.PrimaryKey

// 定義 table 要有什麼欄位
@Entity(tableName = "history-table")
data class HistoryEntity(
    @PrimaryKey
    val date: String
)