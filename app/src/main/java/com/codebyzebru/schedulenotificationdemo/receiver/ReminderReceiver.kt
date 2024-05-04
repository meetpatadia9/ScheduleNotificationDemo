package com.codebyzebru.schedulenotificationdemo.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.codebyzebru.schedulenotificationdemo.baseutils.AppConstants.NotificationKeys.RMNDR_NOTI_TITLE_KEY
import com.codebyzebru.schedulenotificationdemo.notification.ReminderNotification

class ReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val scheduleNotificationService = context?.let { ReminderNotification(it) }
        val title: String? = intent?.getStringExtra(RMNDR_NOTI_TITLE_KEY)
        scheduleNotificationService?.sendReminderNotification(title)
    }
}