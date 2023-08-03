package com.petmily.util

import android.content.Context
import android.content.SharedPreferences
import com.petmily.config.ApplicationClass
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

    fun addUser(user: User) {
        preferences.edit().apply {
            putString("userEmail", user.userEmail)
            putString("userNickname", user.userNickname)
            apply()
        }
    }
    
    
}
