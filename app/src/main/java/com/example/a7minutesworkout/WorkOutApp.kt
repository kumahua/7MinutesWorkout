package com.example.a7minutesworkout

import android.app.Application

class WorkOutApp : Application() {
    // 通過lazy，僅在需要時才創建資料庫，而不是在應用程式啟動時建立
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}