package com.petmily.presentation.view.home

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petmily.R
import com.petmily.databinding.ItemBoardImgBinding
import com.petmily.presentation.view.MainActivity
import java.io.File

private const val TAG = "Fetmily_BoardImgAdapter"
class BoardImgAdapter(
    private val mainActivity: MainActivity,
    private var imgs: List<String> = listOf(),
) : RecyclerView.Adapter<BoardImgAdapter.BoardImgViewHolder>() {
    // TODO: RecyclerView 대신에 ListView로? (로딩 이슈)
    inner class BoardImgViewHolder(val binding: ItemBoardImgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(imgUrl: String) {
            initView(binding, itemView, imgUrl)
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
//        Glide.with(itemView.context)
//            .load(imgUrl)
//            .into(ivBoardImg)
    }
    
    private fun initView(binding: ItemBoardImgBinding, itemView: View, imgUrl: String) = with(binding) {
        // TODO: 이미지 loading dialog 필요
        Glide.with(itemView.context)
            .load(imgUrl)
            .into(ivBoardImg)
    }
    
    @SuppressLint("NotifyDataSetChanged")
    fun setImgs(imgs: List<String>) {
        this.imgs = imgs
        notifyDataSetChanged()
    }
}
