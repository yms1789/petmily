package com.petmily.repository.api.mypage

import android.util.Log
import com.petmily.repository.dto.Board
import com.petmily.repository.dto.Curation
import com.petmily.repository.dto.MypageInfo
import com.petmily.repository.dto.User
import com.petmily.repository.dto.UserProfileResponse
import com.petmily.util.RetrofitUtil
import java.net.ConnectException

private const val TAG = "Petmily_MypageService"
class MypageService {
    /**
     * MainActivity Call
     * UserViewModel Call
     * API - 게시글, 팔로우, 팔로잉, petInfo 불러오기
     */
    suspend fun requestMypageInfo(userEmail: String): MypageInfo {
        return try {
            return RetrofitUtil.mypageApi.requestMypageInfo(userEmail)
        } catch (e: ConnectException) {
            Log.d(TAG, "requestEmailCode ConnectException: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "requestEmailCode Exception: ${e.message}")
            throw Exception()
        }
    }

    /**
     * API - 팔로우 동작
     * userId: 팔로우할 사용자 id
     * user: 나의 userEmail이 담긴 User
     */
    suspend fun followUser(userId: Long, user: User) {
        try {
            RetrofitUtil.mypageApi.followUser(userId, user)
        } catch (e: Exception) {
            Log.d(TAG, "followUser: ${e.message}")
        }
    }

    /**
     * API - 언팔로우 동작
     * userId: 언팔로우할 사용자 id
     * user: 나의 userEmail이 담긴 User
     */
    suspend fun unfollowUser(userId: Long, user: User) {
        try {
            RetrofitUtil.mypageApi.unfollowUser(userId, user)
        } catch (e: Exception) {
            Log.d(TAG, "unfollowUser: ${e.message}")
        }
    }

    /**
     * API - 좋아요 누른 게시물 리스트 조회
     * userEmail: 대상 유저
     * currentUser: 내 이메일 정보
     */
    suspend fun likeBoardList(userEmail: String, currentUser: String): List<Board> {
        return try {
            RetrofitUtil.mypageApi.likeBoardList(userEmail, currentUser)
        } catch (e: Exception) {
            Log.d(TAG, "likeBoardList: ${e.message}")
            listOf()
        }
    }

    /**
     * API - 대상 유저가 팔로우하고 있는 사용자 리스트 조회
     * userEmail: 대상 유저
     * currentUser: 내 이메일 정보
     */
    suspend fun followingList(userEmail: String, currentUser: String): List<UserProfileResponse> {
        return try {
            RetrofitUtil.mypageApi.followingList(userEmail, currentUser)
        } catch (e: Exception) {
            Log.d(TAG, "followingList: ${e.message}")
            listOf()
        }
    }

    /**
     * API - 대상 유저를 팔로우하고 있는 사용자 리스트 조회
     * userEmail: 대상 유저
     * currentUser: 내 이메일 정보
     */
    suspend fun followerList(userEmail: String, currentUser: String): List<UserProfileResponse> {
        return try {
            RetrofitUtil.mypageApi.followerList(userEmail, currentUser)
        } catch (e: Exception) {
            Log.d(TAG, "followerList: ${e.message}")
            listOf()
        }
    }

    /**
     * API - 대상 유저가 북마크한 큐레이션 리스트 조회
     */
    suspend fun userBookmarkedCurations(userEmail: String): List<Curation> {
        return try {
            RetrofitUtil.mypageApi.userBookmarkedCurations(userEmail)
        } catch (e: Exception) {
            Log.d(TAG, "userBookmarkedCurations: ${e.message}")
            listOf()
        }
    }
}
