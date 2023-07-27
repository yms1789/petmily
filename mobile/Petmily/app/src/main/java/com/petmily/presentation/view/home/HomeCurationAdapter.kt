package com.petmily.presentation.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemHomeCurationBinding
import com.petmily.repository.dto.Curation

class HomeCurationAdapter(
    private var curations: List<Curation> = listOf(),
) : RecyclerView.Adapter<HomeCurationAdapter.HomeCurationViewHolder>() {
    
    inner class HomeCurationViewHolder(val binding: ItemHomeCurationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(curation: Curation) = with(binding) {
            // TODO: data binding
            tvCurationTitle.text = curation.curationTitle
            clCuration.setOnClickListener {
                curationClickListener.curationClick(binding, curation, layoutPosition)
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCurationViewHolder {
        return HomeCurationViewHolder(ItemHomeCurationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun getItemCount(): Int = curations.size
    
    override fun onBindViewHolder(holder: HomeCurationViewHolder, position: Int) {
        holder.bindInfo(curations[position % curations.size])
    }
    
    @SuppressLint("NotifyDataSetChanged")
    fun setCurations(curations: List<Curation>) {
        this.curations = listOf(curations.last()) + curations + listOf(curations.first())
        notifyDataSetChanged()
    }
    
    // 이벤트 처리 listener
    interface CurationClickListener {
        fun curationClick(binding: ItemHomeCurationBinding, curation: Curation, position: Int)
    }
    private lateinit var curationClickListener: CurationClickListener
    fun setCurationClickListener(curationClickListener: CurationClickListener) {
        this.curationClickListener = curationClickListener
    }
}
