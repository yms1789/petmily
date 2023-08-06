package com.petmily.presentation.view.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemPointLogBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class PointLogAdapter() :
    RecyclerView.Adapter<PointLogAdapter.PointListViewHolder>() {
    
    inner class PointListViewHolder(val binding: ItemPointLogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo() = with(binding) {
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointListViewHolder {
        return PointListViewHolder(
            ItemPointLogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }
    
    override fun onBindViewHolder(holder: PointListViewHolder, position: Int) {
        // todo 나중에 할거임
    }
    
    override fun getItemCount(): Int {
        return 10
    }
}
