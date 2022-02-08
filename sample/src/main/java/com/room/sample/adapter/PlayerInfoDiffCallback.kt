package com.room.sample.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.room.sample.model.PlayerInfo

class PlayerInfoDiffCallback(
    private val oldList: List<PlayerInfo>,
    private val newList: List<PlayerInfo>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].age === newList[newItemPosition].age
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, oldFirstname, oldLastname, oldAge) = oldList[oldItemPosition]
        val (_, newFirstname, newLastname, newAge) = newList[newItemPosition]
        return oldFirstname == newFirstname && oldLastname == newLastname && oldAge == newAge
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldBean = oldList[oldItemPosition]
        val newBean = newList[newItemPosition]

        val payload = Bundle()
        if (oldBean.age != newBean.age) {
            payload.putInt("age", newBean.age!!)
        }
        if (oldBean.firstName != newBean.firstName) {
            payload.putString("first_name", newBean.firstName!!)
        }
        if (oldBean.lastName != newBean.lastName) {
            payload.putString("last_name", newBean.lastName!!)
        }

        if (payload.size() == 0) return null
        return payload
    }
}