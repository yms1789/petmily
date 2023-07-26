package com.petmily.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemBoardBinding
import com.petmily.repository.dto.Board

class BoardAdapter(
    private var boards: List<Board> = listOf()
): RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {
    
    inner class BoardViewHolder(val binding: ItemBoardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(board: Board) {
            // TODO: data binding
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        return BoardViewHolder(ItemBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun getItemCount(): Int {
        return boards.size
    }
    
    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.bindInfo(boards[position])
    }
}
