package com.petmily.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

class UploadUtil {
    
    /**
     * createMultipartFromUri로 갤러리에서 받아온 사진 multipart를 저장하고 사진을 뷰 바인딩합니다.
     */
//    private val galleryActivityResult =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//            // 결과를 처리하는 코드를 작성합니다.
//            // uri를 multipart로 변환한다
//            if (uri != null) {
//                uploadFragmentViewModel.setMultipart(
//                    createMultipartFromUri(
//                        requireContext(),
//                        uri
//                    )!!
//                )
//            }
//            
//            // 선택한 이미지의 Uri를 처리하는 코드를 작성합니다.
//            Glide.with(this)
//                .load(uri)
//                .transform(CenterCrop(), RoundedCorners(20))
//                .into(binding.imageSelectedPhoto)
//        }

    /**
     * uri로 multipart 객체를 만듭니다.
     */
    fun createMultipartFromUri(context: Context, uri: Uri): MultipartBody.Part? {
        val file: File? = getFileFromUri(context, uri)
        if (file == null) {
            // 파일을 가져오지 못한 경우 처리할 로직을 작성하세요.
            return null
        }
        
        val requestFile: RequestBody = createRequestBodyFromFile(file)
        return MultipartBody.Part.createFormData("multipartFiles", file.name, requestFile)
    }
    
    /**
     * uri로 사진 파일을 가져옵니다
     * createMultipartFromUri로 결과값을 반환합니다
     */
    fun getFileFromUri(context: Context, uri: Uri): File? {
        val filePath = uriToFilePath(context, uri)
        return if (filePath != null) File(filePath) else null
    }
    
    /**
     * 만들어진 uri를 파일로 변환합니다
     */
    @SuppressLint("Range")
    fun uriToFilePath(context: Context, uri: Uri): String? {
        lateinit var filePath: String
        context.contentResolver.query(uri, null, null, null, null).use { cursor ->
            cursor?.let {
                if (it.moveToFirst()) {
                    val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    val file = File(context.cacheDir, displayName)
                    try {
                        val inputStream = context.contentResolver.openInputStream(uri)
                        val outputStream = FileOutputStream(file)
                        inputStream?.copyTo(outputStream)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    filePath = file.absolutePath
                }
            }
        }
        return filePath
    }
    
    /**
     * 저장된 사진 파일의 body를 가져옵니다
     */
    private fun createRequestBodyFromFile(file: File): RequestBody {
        val MEDIA_TYPE_IMAGE = "multipart/form-data".toMediaTypeOrNull()
        val inputStream: InputStream = FileInputStream(file)
        val byteArray = inputStream.readBytes()
        return RequestBody.create(MEDIA_TYPE_IMAGE, byteArray)
    }
}
