package com.room.sample.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.room.sample.databinding.LayoutItemBinding
import com.room.sample.model.PlayerInfo

class PlayerInfoListAdapter : RecyclerView.Adapter<PlayerInfoListAdapter.PlayInfoViewHolder>() {

    private var infoList = mutableListOf<PlayerInfo>()

    private var itemOnClickCallback: ((PlayerInfo) -> Unit)? = null

    fun addOnItemClickCallback(func: (PlayerInfo) -> Unit) {
        itemOnClickCallback = func
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayInfoViewHolder {
        val layoutItemBinding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context))
        return PlayInfoViewHolder(layoutItemBinding)
    }

    override fun onBindViewHolder(holder: PlayInfoViewHolder, position: Int) {
        val playerInfo = infoList[position]
        holder.bindData(playerInfo)
    }

    override fun onBindViewHolder(
        holder: PlayInfoViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            this.onBindViewHolder(holder, position)
        } else {
            val bundle = payloads[0] as Bundle
            val playerInfo = infoList[position]
            for (key in bundle.keySet()) {
                when (key) {
                    "age" -> holder.tvAge.text = playerInfo.age.toString()
                    "first_name" -> holder.tvFirstname.text = playerInfo.firstName
                    "last_name" -> holder.tvLastname.text = playerInfo.lastName
                }
            }
        }
    }

    override fun getItemCount(): Int = infoList.size

    fun refreshDataList(newList: List<PlayerInfo>) {
        val callback = PlayerInfoDiffCallback(infoList, newList)
        val diffResult = DiffUtil.calculateDiff(callback)

        infoList.clear()
        infoList.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }

    // ViewHolder
    inner class PlayInfoViewHolder(private val layoutItemBinding: LayoutItemBinding) :
        RecyclerView.ViewHolder(layoutItemBinding.root) {

        val tvAge = layoutItemBinding.tvAge
        val tvFirstname = layoutItemBinding.tvFirstname
        val tvLastname = layoutItemBinding.tvLastname

        init {
            itemView.setOnClickListener {
                itemOnClickCallback?.let { func -> func(infoList[adapterPosition]) }
            }
        }

        fun bindData(info: PlayerInfo) {
            info.run {
                layoutItemBinding.tvFirstname.text = firstName
                layoutItemBinding.tvLastname.text = lastName
                layoutItemBinding.tvAge.text = age.toString()
            }
        }
    }

}