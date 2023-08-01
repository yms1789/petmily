package com.petmily.presentation.view.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemBoardSelectImageBinding

class ImageAdapter :
    RecyclerView.Adapter<ImageAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(binding: ItemBoardSelectImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindInfo() {
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ImageAdapter.CustomViewHolder {
        return CustomViewHolder(ItemBoardSelectImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImageAdapter.CustomViewHolder, position: Int) {
        holder.bindInfo()
    }

    override fun getItemCount(): Int {
        return 10
    }
}
