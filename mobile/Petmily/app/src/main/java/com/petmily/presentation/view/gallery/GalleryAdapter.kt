package com.petmily.presentation.view.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petmily.databinding.ItemGalleryPhotoBinding
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.repository.dto.Photo

class GalleryAdapter(var lifecycleOwner: LifecycleOwner, var activityViewModel: MainViewModel) :
    RecyclerView.Adapter<GalleryAdapter.GalleryHolder>() {

    inner class GalleryHolder(binding: ItemGalleryPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val itemImage = binding.ivItemImage
        val checkBox = binding.cbBox

        fun bindInfo(product: Photo) {
            product.isSelected.observe(lifecycleOwner) { isSelected ->
                // isSelected 값이 변경되었을 때 실행되는 코드
                checkBox.isChecked = isSelected
                if (checkBox.isChecked == true) {
                    itemImage.alpha = 0.5f
                } else {
                    itemImage.alpha = 1.0f
                }
            }

            Glide.with(itemView)
                .load(product.imgUrl)
                .into(itemImage)

            // 체크박스 or 사진 선택시 값 변경
            itemImage.setOnClickListener {
                product.isSelected.value = !product.isSelected.value!!
            }

            checkBox.setOnClickListener {
                product.isSelected.value = !product.isSelected.value!!
            }

            // 하나만 선택
//            checkbox.setOnClickListener {
//                if (selectedCnt == maxSelectedCnt) { // 이미 한 개 선택되어 있다면
//                    selectedPhoto.isSelected.value = !selectedPhoto.isSelected.value!! // 기존에 선택된 애 해제
//                    selectedPhoto = product // 이번에 선택한 놈이 선택됨!
//                    product.isSelected.value = !product.isSelected.value!!
//                } else { // 처음 선택하는 것이라면
//                    product.isSelected.value = !product.isSelected.value!!
//                    selectedPhoto = product
//                    selectedCnt++
//                }
//            }

//            itemView.setOnClickListener{
//                itemClickListner.onClick(it, layoutPosition, productList[layoutPosition].id)
//            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GalleryAdapter.GalleryHolder {
        return GalleryHolder(ItemGalleryPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
        holder.apply {
            bindInfo(activityViewModel.galleryList.value!!.get(position))
        }
    }

    override fun getItemCount(): Int {
        return activityViewModel.galleryList.value!!.size
    }

//    //클릭 인터페이스 정의 사용하는 곳에서 만들어준다.
//    interface ItemClickListener {
//        fun onClick(view: View,  position: Int, productId:Int)
//    }

//    //클릭리스너 선언
//    private lateinit var itemClickListner: ItemClickListener

//    //클릭리스너 등록 매소드
//    fun setItemClickListener(itemClickListener: ItemClickListener) {
//        this.itemClickListner = itemClickListener
//    }
}
