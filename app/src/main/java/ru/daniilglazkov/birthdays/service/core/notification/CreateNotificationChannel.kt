package ru.daniilglazkov.birthdays.service.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import ru.daniilglazkov.birthdays.R

/**
 * @author Danil Glazkov on 14.03.2023, 1:49
 */
interface CreateNotificationChannel {

    fun createChannel()


    abstract class Abstract(
        private val context: Context,
        private val channel: String,
        private val name: String,
        private val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ) : CreateNotificationChannel {
        override fun createChannel() = context.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(NotificationChannel(channel, name, importance))
    }


    class Reminders(context: Context) : Abstract(
        context = context,
        channel = context.getString(R.string.reminders_channel_id),
        name = context.getString(R.string.reminders),
    )
}