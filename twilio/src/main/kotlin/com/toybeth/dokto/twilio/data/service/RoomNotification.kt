package com.toybeth.dokto.twilio.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.toybeth.dokto.twilio.R
import com.toybeth.dokto.twilio.ui.call.TwilioCallActivity

private const val VIDEO_SERVICE_CHANNEL = "VIDEO_SERVICE_CHANNEL"
const val ONGOING_NOTIFICATION_ID = 1

class RoomNotification(private val context: Context) {

    private val pendingIntent
            get() =
                Intent(context, TwilioCallActivity::class.java).let { notificationIntent ->
                    notificationIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    PendingIntent.getActivity(context, 0, notificationIntent, 0)
                }

    init {
        createDownloadNotificationChannel(VIDEO_SERVICE_CHANNEL,
                context.getString(R.string.room_notification_channel_title),
                context)
    }

    fun buildNotification(roomName: String): Notification =
            NotificationCompat.Builder(context, VIDEO_SERVICE_CHANNEL)
                    .setContentTitle(context.getString(R.string.room_notification_title, roomName))
                    .setContentText(context.getString(R.string.room_notification_message))
                    .setContentIntent(pendingIntent)
                    .setUsesChronometer(true)
                    .setSmallIcon(R.drawable.ic_video_call_white_24dp)
                    .setTicker(context.getString(R.string.room_notification_message))
                    .build()

    private fun createDownloadNotificationChannel(channelId: String, channelName: String, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW).apply {
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
