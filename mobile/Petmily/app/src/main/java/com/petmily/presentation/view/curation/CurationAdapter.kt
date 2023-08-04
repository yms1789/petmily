package com.petmily.presentation.view.curation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petmily.databinding.ItemCurationBinding
import com.petmily.repository.dto.Curation

class CurationAdapter(val curationList: MutableList<Curation>?) :
    RecyclerView.Adapter<CurationAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(binding: ItemCurationBinding) : RecyclerView.ViewHolder(binding.root) {
        private val title = binding.tvCurationItemTitle
        private val image = binding.ivCurationItemImage
        private val cdCuration = binding.cdCuration

        fun bindInfo(curation: Curation) {
            title.text = curation.ctitle

            Glide.with(itemView.context)
                .load(curation.cimage)
                .into(image)

            cdCuration.setOnClickListener {
                itemClickListener.onClick(it, curation, layoutPosition)
            }
        }
    }

    // click Event
    interface ItemClickListener {
        fun onClick(view: View, curation: Curation, position: Int)
    }
    lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CurationAdapter.CustomViewHolder {
        return CustomViewHolder(ItemCurationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CurationAdapter.CustomViewHolder, position: Int) {
        holder.bindInfo(curationList?.get(position) ?: Curation())
    }

    override fun getItemCount(): Int {
        return curationList?.size ?: 0
    }
}
