package com.jiajieshen.android.samples.features.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.features.main.MainActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by xin on 7/12/17.
 */

class AppFirebaseMessagingService : FirebaseMessagingService(), AnkoLogger {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        info("firebase : From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            info("firebase : Message data payload: " + remoteMessage.data)

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                // scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
        }


        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            info("firebase : Message Notification Body: " + remoteMessage.notification.body!!)
            sendNotification(remoteMessage.notification.body!!)
        }

    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        info("firebase : Short lived task is done.")
    }

    /**
     * Create and show a simple notification containing the received FCM message.

     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}
