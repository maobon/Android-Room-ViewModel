package com.room.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.room.sample.db.PlayerInfoRepo
import com.room.sample.model.PlayerInfo
import kotlinx.coroutines.launch

class PlayerInfoViewModel(private val repo: PlayerInfoRepo) : ViewModel() {

    private val dataSet: MutableLiveData<List<PlayerInfo>> by lazy {
        queryPlayerInfos()
        MutableLiveData<List<PlayerInfo>>()
    }

    fun getDataSet(): LiveData<List<PlayerInfo>> = dataSet

    private fun queryPlayerInfos() {
        viewModelScope.launch {
            query()
        }
    }

    fun addPlayerInfo(info: PlayerInfo) {
        viewModelScope.launch {
            repo.addNewPlayerInfo(info)
            query()
        }
    }

    fun queryPlayerByAge(age: Int) {
        viewModelScope.launch {
            val ret = repo.getPlayersByAge(age)
            dataSet.postValue(ret)
        }
    }

    fun updatePlayerInfo(info: PlayerInfo) {
        viewModelScope.launch {
            repo.updatePlayerInfo(info)
            query()
        }
    }

    fun deleteSelectPlayInfo(info: PlayerInfo) {
        viewModelScope.launch {
            repo.deletePlayerInfo(info)
            query()
        }
    }

    // ...
    private suspend fun query() {
        val playerInfoList = repo.getPlayerInfoList(orderByDESC = true)
        dataSet.postValue(playerInfoList)
    }

}