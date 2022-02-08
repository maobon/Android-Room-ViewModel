package com.room.sample.db.dao

import androidx.room.*
import com.room.sample.model.PlayerInfo

@Dao
interface PlayerInfoDao {

    @Query(
        "SELECT * FROM player_info " +
                "ORDER BY " +
                "CASE WHEN :orderDESC = 0 THEN player_info.age END ASC, " +
                "CASE WHEN :orderDESC = 1 THEN player_info.age END DESC"
    )
    suspend fun queryAll(orderDESC: Boolean? = false): List<PlayerInfo>

    @Query(
        "SELECT * FROM player_info " +
                "WHERE player_info.age >= :minAge " +
                "ORDER BY player_info.age DESC"
    )
    suspend fun queryPlayersByAge(minAge: Int): List<PlayerInfo>

    @Insert
    suspend fun insert(vararg info: PlayerInfo)

    @Update
    suspend fun alter(info: PlayerInfo)

    @Delete
    suspend fun delete(info: PlayerInfo)
}