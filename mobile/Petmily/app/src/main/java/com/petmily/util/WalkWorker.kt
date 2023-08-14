package com.petmily.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.petmily.R
import com.petmily.presentation.view.MainActivity
import kotlinx.coroutines.delay

private const val TAG = "SHIT_WalkWorker"
class WalkWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {
    
    private val locationManager by lazy {
        // ~~Manager는 getSystemService로 호출
        applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private lateinit var location1: Location
    private lateinit var location2: Location
    
    var totalDist = 0F
    var time = 0
    
    private val handler = Handler(Looper.getMainLooper())
    
    private val notificationContent = "Foreground Service is running"
    
    override suspend fun doWork(): Result {
        val foregroundInfo = createForegroundInfo(notificationContent)
        setForegroundAsync(foregroundInfo)
        
        handler.post {
            getProviders()
        }
        // Simulate receiving location updates
        simulateLocationUpdates()
        
        return Result.success()
    }
    
    // Creates an instance of ForegroundInfo which can be used to update the
    // ongoing notification.
    private fun createForegroundInfo(progress: String): ForegroundInfo {
        createNotificationChannel()
        
//        val id = applicationContext.getString(R.string.notification_channel_id)
        val title = applicationContext.getString(R.string.app_name)
        
//        // notification 취소 버튼
//        // This PendingIntent can be used to cancel the worker
//        val cancel = applicationContext.getString(R.string.cancel_download)
//        val cancelIntent = WorkManager.getInstance(applicationContext)
//            .createCancelPendingIntent(getId())
        
//        // Create a Notification channel if necessary
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createChannel()
//        }
        
        val notification = NotificationCompat.Builder(applicationContext, FOREGROUND_SERVICE_ID.toString())
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setSmallIcon(R.drawable.main_logo_small)
            .setOngoing(true)
            // Add the cancel action to the notification which can
            // be used to cancel the worker
//            .addAction(android.R.drawable.ic_delete, cancel, cancelIntent)
            .build()
        
        return ForegroundInfo(FOREGROUND_SERVICE_ID, notification)
    }
    
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        // Create a Notification channel
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager: NotificationManager =
                applicationContext.getSystemService(NotificationManager::class.java)
            val serviceChannel = NotificationChannel(
//                applicationContext.getString(R.string.notification_channel_id),
                FOREGROUND_SERVICE_ID.toString(),
                applicationContext.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_NONE,
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }
    
    private suspend fun simulateLocationUpdates() {
        // Simulate receiving location updates and update the notification
        while (true) {
//            val location = simulateLocation(i)
//            updateNotification("Location: ${location.latitude}, ${location.longitude}")
            delay(1000) // Simulate delay between updates
            if (this::location1.isInitialized) {
                totalDist += location1.distanceTo(location2)
                MainActivity.walkDist = totalDist
                updateNotification("산책 거리 : $totalDist \n산책 시간 : $time")
                
                
                Log.d(TAG, "simulateLocationUpdates: 이동거리 $totalDist")
            }
            if (this::location2.isInitialized) {
                location1 = location2
            }
            time++
            MainActivity.walkTime = time
            updateNotification("산책 거리 : $totalDist \n산책 시간 : $time")
        }
    }
    
    private fun updateNotification(content: String) {
        val foregroundInfo = createForegroundInfo(content)
        setForegroundAsync(foregroundInfo)
    }
    
    companion object {
        private const val FOREGROUND_SERVICE_ID = 12345
    }
    
    @SuppressLint("MissingPermission")
    private fun getProviders() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            listener,
        )
    }
    
    private val listener = object : LocationListener {
        // 위치가 변경될때 호출될 method
        @SuppressLint("SetTextI18n") // set text internationalization : 문자를 국제화에 맞춤
        override fun onLocationChanged(location: Location) {
            when (location.provider) {
                LocationManager.GPS_PROVIDER -> {
                    location2 = location
                }
            }
        }
        
        @SuppressLint("MissingPermission")
        override fun onProviderEnabled(provider: String) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        }
    }
}
