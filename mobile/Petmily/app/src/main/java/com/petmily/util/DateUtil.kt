package com.petmily.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

class DateUtil {
    companion object {
        private var year = 0
        private var month = 0
        private var day = 0
        private var hour = 0
        private var minute = 0
        
        private var nYear = 0
        private var nMonth = 0
        private var nDay = 0
        private var nHour = 0
        private var nMinute = 0
        
        @SuppressLint("SimpleDateFormat")
        private fun setNow() {
            SimpleDateFormat("yyyy-MM-dd-hh-mm")
                .format(Date(System.currentTimeMillis()))
                .split("-")
                .apply {
                    nYear = this[0].toInt()
                    nMonth = this[1].toInt()
                    nDay = this[2].toInt()
                    nHour = this[3].toInt()
                    nMinute = this[4].toInt()
                }
        }
        
        fun uploadDateFormat(date: String): String {
            try {
                setNow()
    
                year = date.substring(0..3).toInt()
                month = date.substring(4..5).toInt()
                day = date.substring(6..7).toInt()
                hour = date.substring(11..12).toInt()
                minute = date.substring(14..15).toInt()
    
                return if (nYear < year) {
                    "${year - nYear}년 전"
                } else if (nMonth < month) {
                    "${month - nMonth}달 전"
                } else if (nDay < day) {
                    "${day - nDay}일 전"
                } else if (nHour < hour) {
                    "${hour - nHour}시간 전"
                } else if (nMinute < minute) {
                    "${minute - nMinute}분 전"
                } else {
                    "방금 전"
                }
            } catch (e: Exception) {
                return ""
            }
        }
    }
}
