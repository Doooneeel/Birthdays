package ru.daniilglazkov.birthdays.service.core.notification

import android.app.NotificationManager.*
import android.content.Context
import androidx.core.app.NotificationManagerCompat

/**
 * @author Danil Glazkov on 21.03.2023, 1:38
 */
interface AreNotificationEnabled {

    fun areEnabled(id: String): Boolean


    class Base(context: Context) : AreNotificationEnabled {

        private val manager = NotificationManagerCompat.from(context)

        override fun areEnabled(id: String): Boolean {
            val notificationsEnabled = manager.areNotificationsEnabled()

            return notificationsEnabled && manager.notificationChannelsCompat.any { notification ->
                notification.id == id && notification.importance != IMPORTANCE_NONE
            }
        }
    }
}