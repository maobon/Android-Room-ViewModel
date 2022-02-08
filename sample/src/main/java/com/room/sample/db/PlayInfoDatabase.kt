package com.room.sample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.room.sample.db.dao.PlayerInfoDao
import com.room.sample.model.PlayerInfo

@Database(entities = [PlayerInfo::class], version = 1, exportSchema = false)
abstract class PlayInfoDatabase : RoomDatabase() {

    abstract fun getPlayerInfoDao(): PlayerInfoDao

    companion object {
        private var ourInstance: PlayInfoDatabase? = null

        fun getInstance(context: Context): PlayInfoDatabase {
            val instance = ourInstance
            if (instance != null) return instance

            synchronized(this) {
                val database =
                    Room.databaseBuilder(context, PlayInfoDatabase::class.java, "player_info.db")
                        .build()

                ourInstance = database
                return database
            }
        }
    }
}