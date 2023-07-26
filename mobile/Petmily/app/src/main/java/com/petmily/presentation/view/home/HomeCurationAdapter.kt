package com.petmily.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemHomeCurationBinding
import com.petmily.repository.dto.Curation

class HomeCurationAdapter(
    private var curations: List<Curation> = listOf(),
) : RecyclerView.Adapter<HomeCurationAdapter.HomeCurationViewHolder>() {
    
    inner class HomeCurationViewHolder(val binding: ItemHomeCurationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(curation: Curation) {
            // TODO: data binding
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCurationViewHolder {
        return HomeCurationViewHolder(ItemHomeCurationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun getItemCount(): Int {
        return curations.size
    }
    
    override fun onBindViewHolder(holder: HomeCurationViewHolder, position: Int) {
        holder.bindInfo(curations[position])
    }
}
