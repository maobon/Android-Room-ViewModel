package com.room.sample.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.room.sample.PlayerInfoViewModel
import com.room.sample.db.PlayerInfoRepo

// ViewModel provider factory
class ViewModelProviderFactory(private val repo: PlayerInfoRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerInfoViewModel::class.java)) {
            return PlayerInfoViewModel(repo) as T
        }
        throw IllegalArgumentException("can not find suitable class")
    }
}