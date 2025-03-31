package com.dkproject.space_out.service

import android.Manifest
import android.app.Notification
import android.app.Notification.MediaStyle
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.PermissionChecker
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaStyleNotificationHelper
import com.dkproject.space_out.R

class AudioService: Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground()
        return super.onStartCommand(intent, flags, startId)
    }

    @OptIn(UnstableApi::class)
    private fun createNotification(): Notification {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(this.getString(R.string.app_name))
            .setContentText(this.getString(R.string.notificationtext))
            .setOngoing(true)
            .build()
    }

    private fun startForeground() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermission = PermissionChecker.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            if(notificationPermission != PermissionChecker.PERMISSION_GRANTED) {
                Log.d("AudioService", "Notification permission not granted")
                stopSelf()
                return
            }
        }
        startForeground(1, createNotification())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AudioService", "onDestroy")
    }

    companion object {
        const val CHANNEL_ID = "audio_service"
        const val CHANNEL_NAME = "Audio Service"
    }
}