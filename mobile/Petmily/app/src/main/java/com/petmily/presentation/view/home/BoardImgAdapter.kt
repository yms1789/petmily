package com.petmily.presentation.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.R
import com.petmily.databinding.ItemBoardImgBinding

class BoardImgAdapter(
    private var imgs: List<String> = listOf(),
) : RecyclerView.Adapter<BoardImgAdapter.BoardImgViewHolder>() {
    
    inner class BoardImgViewHolder(val binding: ItemBoardImgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(img: String) = with(binding) {
            // TODO: Glide
            binding.ivBoardImg.setImageResource(R.drawable.main_logo_big)
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardImgViewHolder {
        return BoardImgViewHolder(ItemBoardImgBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun getItemCount(): Int {
        return imgs.size
    }
    
    override fun onBindViewHolder(holder: BoardImgViewHolder, position: Int) {
        holder.bindInfo(imgs[position])
    }
    
    @SuppressLint("NotifyDataSetChanged")
    fun setImgs(imgs: List<String>) {
        this.imgs = imgs
        notifyDataSetChanged()
    }
}
