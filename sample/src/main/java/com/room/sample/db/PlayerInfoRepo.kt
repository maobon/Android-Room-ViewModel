package com.room.sample.db

import android.app.Application
import com.room.sample.db.dao.PlayerInfoDao
import com.room.sample.model.PlayerInfo

class PlayerInfoRepo(application: Application) {

    private val database: PlayInfoDatabase =
        PlayInfoDatabase.getInstance(application.applicationContext)

    private val dao: PlayerInfoDao = database.getPlayerInfoDao()

    suspend fun getPlayerInfoList(orderByDESC: Boolean): List<PlayerInfo> =
        dao.queryAll(orderByDESC)

    suspend fun addNewPlayerInfo(info: PlayerInfo) = dao.insert(info)

    suspend fun getPlayersByAge(minAge: Int): List<PlayerInfo> = dao.queryPlayersByAge(minAge)

    suspend fun updatePlayerInfo(info: PlayerInfo) = dao.alter(info)

    suspend fun deletePlayerInfo(info: PlayerInfo) = dao.delete(info)

}