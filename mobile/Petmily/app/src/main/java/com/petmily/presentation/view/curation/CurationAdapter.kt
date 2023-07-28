package com.petmily.presentation.view.curation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemCurationBinding

class CurationAdapter() :
    RecyclerView.Adapter<CurationAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(binding: ItemCurationBinding) : RecyclerView.ViewHolder(binding.root) {
        private val title = binding.tvCurationItemTitle
        private val image = binding.ivCurationItemImage

        fun bindInfo() {
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CurationAdapter.CustomViewHolder {
        return CustomViewHolder(ItemCurationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CurationAdapter.CustomViewHolder, position: Int) {
        holder.bindInfo()
    }

    override fun getItemCount(): Int {
        return 10
    }
}
