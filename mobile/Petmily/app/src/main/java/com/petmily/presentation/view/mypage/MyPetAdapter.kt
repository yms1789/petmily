package com.petmily.presentation.view.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemMypageMypetLastBinding
import com.petmily.databinding.ItemMypageMypetNormalBinding
import com.petmily.repository.dto.Pet

data class NormalItem(val pet: Pet)
data class LastItem(val pet: Pet)

class MyPetAdapter(
    private val items: MutableList<Pet>,
    private val onNormalItemClick: (NormalItem) -> Unit,
    private val onLastItemClick: (LastItem) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_NORMAL = 0
    private val VIEW_TYPE_LAST = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val binding = ItemMypageMypetNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NormalItemViewHolder(binding)
            }
            VIEW_TYPE_LAST -> {
                val binding = ItemMypageMypetLastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LastItemViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is NormalItemViewHolder -> {
                val normalItem = item as NormalItem
                holder.bind(normalItem)
                holder.itemView.setOnClickListener {
                    onNormalItemClick(normalItem)
                }
            }
            is LastItemViewHolder -> {
                val lastItem = item as LastItem
                holder.bind(lastItem)
                holder.itemView.setOnClickListener {
                    onLastItemClick(lastItem)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].petId == 0L) {
            // 펫이 아님
            VIEW_TYPE_LAST
        } else {
            VIEW_TYPE_NORMAL
        }
//        return when (items[position]) {
//            is NormalItem -> VIEW_TYPE_NORMAL
//            is LastItem -> VIEW_TYPE_LAST
//            else -> throw IllegalArgumentException("Invalid item type")
//        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class NormalItemViewHolder(private val binding: ItemMypageMypetNormalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NormalItem) {
            binding.tvPetName.text = item.pet.petName
        }
    }

    inner class LastItemViewHolder(private val binding: ItemMypageMypetLastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LastItem) {
//            binding.textView.text = item.text
        }
    }
}
