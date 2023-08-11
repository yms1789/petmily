package com.petmily.presentation.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petmily.databinding.ItemSearchCurationBinding
import com.petmily.repository.dto.Curation

class SearchCurationAdapter(
    private var curations: List<Curation> = listOf(),
) : RecyclerView.Adapter<SearchCurationAdapter.SearchCurationViewHolder>() {

    inner class SearchCurationViewHolder(val binding: ItemSearchCurationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(curation: Curation) = with(binding) {
            Glide.with(itemView)
                .load(curation.cimage)
                .into(ivCurationItemImage)
            tvCurationItemTitle.text = curation.ctitle
            root.setOnClickListener {
                curationClickListener.curationClick(binding, curation, layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCurationViewHolder {
        return SearchCurationViewHolder(
            ItemSearchCurationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int = curations.size

    override fun onBindViewHolder(holder: SearchCurationViewHolder, position: Int) {
        holder.bindInfo(curations[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCurations(curations: List<Curation>) {
        this.curations = curations
        notifyDataSetChanged()
    }

    // 이벤트 처리 listener
    interface CurationClickListener {
        fun curationClick(binding: ItemSearchCurationBinding, curation: Curation, position: Int)
    }
    private lateinit var curationClickListener: CurationClickListener
    fun setCurationClickListener(curationClickListener: CurationClickListener) {
        this.curationClickListener = curationClickListener
    }
}
