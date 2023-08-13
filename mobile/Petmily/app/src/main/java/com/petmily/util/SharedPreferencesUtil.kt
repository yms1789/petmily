package com.petmily.util

import android.content.Context
import android.content.SharedPreferences
import com.petmily.config.ApplicationClass
import com.petmily.config.ApplicationClass.Companion.REFRESH_TOKEN
import com.petmily.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.petmily.repository.dto.User

class SharedPreferencesUtil(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun addUserCookie(cookies: HashSet<String>) {
        val editor = preferences.edit()
        editor.putStringSet(ApplicationClass.COOKIES_KEY_NAME, cookies)
        editor.apply()
    }

    fun getUserCookie(): MutableSet<String>? {
        return preferences.getStringSet(ApplicationClass.COOKIES_KEY_NAME, HashSet())
    }

    fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    fun getLong(key: String): Long {
        return preferences.getLong(key, 0L)
    }

    fun addUser(user: User) {
        preferences.edit().apply {
            putLong("userId", user.userId)
            putString("userEmail", user.userEmail)
            putString("userNickname", user.userNickname)
            putString("userProfileImg", user.userProfileImg)
            putString(REFRESH_TOKEN, user.userToken)
            putLong("userRing", user.userRing)
            putLong("userBadge", user.userBadge)
            apply()
        }
    }

    fun addAccessToken(token: String) {
        preferences.edit().apply {
            putString(X_ACCESS_TOKEN, token)
            apply()
        }
    }

    fun removeUser() {
        preferences.edit().clear().apply()
    }

    /**
     *  출석 yyyyMMDD(string)
     *  마지막 출석 날짜를 리턴
     */
//    fun setAttendanceTime(time: String) {
//        preferences.edit().apply {
//            putString("attendanceTime", time)
//            apply()
//        }
//    }
//    fun getAttendanceTime(): String? {
//        return preferences.getString("attendanceTime", null)
//    }
}
