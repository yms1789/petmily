package com.petmily.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.repository.dto.Photo
import java.io.ByteArrayOutputStream
import java.io.File
private const val TAG = "petmily_GalleryUtil"
class GalleryUtil {
    
    /**
     * 갤러리에서 선택한 이미지 url을 base64문자열로 변환
     * AWS 버킷에서 이미지를 받아올 때는 별도 변환 과정 없이 바로 사용 가능
     */
    fun convertImageToBase64(imagePath: String): String? {
        val file = Uri.fromFile(File(imagePath))
        Log.d(TAG, "convertImageToBase64: $file / imagepath: $imagePath ")
        
        // 이미지 파일을 Bitmap으로 디코딩
        val bitmap = BitmapFactory.decodeFile(imagePath)
        
        if (bitmap != null) {
            // 이미지를 JPEG 형식으로 압축하여 ByteArrayOutputStream에 기록
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            
            // ByteArrayOutputStream을 바이트 배열로 변환
            val byteArray = byteArrayOutputStream.toByteArray()
            
            // 바이트 배열을 Base64 인코딩하여 문자열로 변환
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }
        
        return null
    }

    fun getImages(context: Context, mainViewModel: MainViewModel): Boolean {
        // 사진 선택을 위해 이전에 선택했던 사진들은 모두 초기화
        mainViewModel.emptyGalleryList()

        // 가져올 이미지의 속성을 지정. 여기서는 이미지 ID, 이미지 파일 이름, 이미지 파일 경로를 가져옴
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
        )

        // 이미지를 추가된 날짜의 역순으로 정렬하여 최신 이미지부터 가져옴
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // 외부 저장소의 이미지 컨텐츠를 가리키는 URI를 사용하여 이미지 정보를 가져옴
            projection,
            null,
            null,
            sortOrder,
        )

        // 커서로 앨범의 이미지를 하나씩 addToGalleryList에 저장
        cursor?.use { cursor ->
            val idColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dataColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (cursor.moveToNext()) {
                val imageId = cursor.getLong(idColumnIndex)
                val imageName = cursor.getString(nameColumnIndex)
                val imageData = cursor.getString(dataColumnIndex)
                Log.d("gallery", "loadPhotos: $imageName")
                // 이미지 데이터를 사용하여 필요한 작업 수행
                mainViewModel.addToGalleryList(
                    Photo().apply {
                        imgUrl = imageData
                        isSelected.value = false
                    },
                )
            }
        }

        return true
    }
}
