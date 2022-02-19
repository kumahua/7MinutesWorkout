package com.example.a7minutesworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    /**
     * 將資料庫連接至DAO
     */
    abstract fun historyDao(): HistoryDao

    //官方推薦的 Singleton 寫法，因為實體的產生很耗資源，而且也不需要多個資料庫實體
    companion object{
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        /**
         * INSTANCE 將保留對通過 getInstance 返回的任何資料庫的引用。
         * 這將幫助我們避免重複初始化資料庫，這是浪費的。
         * volatile 變量的值永遠不會被緩存，所有寫入和讀取都將在主內存中完成
         * 這意味著一個Thread對共享資料所做的更改對其他Thread是可見的
         */

        fun getInstance(context: Context): HistoryDatabase {
            // 多執行緒(Multiple threads)可以同時請求資料庫，確保我們只使用同步初始化一次。 一次只能有一個執行續進入同步塊。
            synchronized(this) {

                // 將 INSTANCE 的當前值複製到局部變量，以便 Kotlin 可以智能轉換。
                // Smart cast is only available to local variables.
                var instance = INSTANCE

                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history_database"
                    )
                        // 如果沒有 Migration 對象，則抹除並重建而不是遷移。
                        // Migration is not part of this lesson. You can learn more about
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }

                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}