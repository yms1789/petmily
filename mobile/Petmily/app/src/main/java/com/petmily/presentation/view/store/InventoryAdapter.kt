package com.petmily.presentation.view.store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petmily.databinding.ItemShopBinding
import com.petmily.repository.dto.Shop

class InventoryAdapter() :
    RecyclerView.Adapter<InventoryAdapter.ShopViewHolder>() {

    private var itemList: MutableList<Shop> = mutableListOf()

    inner class ShopViewHolder(val binding: ItemShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(shop: Shop) = with(binding) {
            Glide.with(itemView)
                .load(shop.itemImg)
                .into(ivItemImage)

            tvItemName.setText(shop.itemName)

            // 클릭 이벤트
            ivItemImage.setOnClickListener {
                itemClickListener.itemClick(it, shop, layoutPosition)
            }
        }
    }

    interface ItemClickListener {
        fun itemClick(view: View, item: Shop, position: Int)
    }
    lateinit var itemClickListener: ItemClickListener

    fun setMyItemList(items: MutableList<Shop>) {
        itemList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        return ShopViewHolder(
            ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bindInfo(itemList.get(position))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
