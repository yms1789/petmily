package com.petmily.presentation.view.walk

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemWalkListBinding
import com.petmily.repository.dto.WalkInfo

class WalkListAdapter : RecyclerView.Adapter<WalkListAdapter.WalkListViewHolder>() {

    private var walkInfoList = listOf<WalkInfo>()

    inner class WalkListViewHolder(private val binding: ItemWalkListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(walkInfo: WalkInfo) = with(binding) {
            // 클릭 시 리스트에서 제거
            tvWalkPetName.text = walkInfo.pet.petName
            tvWalkTime.text = walkInfo.walkSpend.toString()
            tvWalkDist.text = walkInfo.walkDistance.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WalkListViewHolder {
        return WalkListViewHolder(
            ItemWalkListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: WalkListViewHolder, position: Int) {
        holder.bindInfo(walkInfoList[position])
    }

    override fun getItemCount(): Int {
        return walkInfoList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setWalkInfoList(walkInfoList: List<WalkInfo>) {
        this.walkInfoList = walkInfoList
        notifyDataSetChanged()
    }
}
