package ru.daniilglazkov.birthdays.service.birthday.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat.*
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.service.intents.CreatePendingIntent

/**
 * @author Danil Glazkov on 16.03.2023, 16:56
 */
interface BirthdayNotification {

    fun notify(context: Context)


    abstract class Abstract(
        private val notificationId: Int,
        @StringRes private val channelId: Int,
    ) : BirthdayNotification {

        protected abstract fun buildNotification(base: Builder): Builder

        protected abstract fun pendingIntent(context: Context): PendingIntent


        override fun notify(context: Context) = try {
            val notificationManager = context.getSystemService(NotificationManager::class.java)

            val baseBuilder = Builder(context, context.getString(channelId))
                .setSmallIcon(R.drawable.ic_celebration_fill_small)
                .setPriority(PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent(context))
                .setAutoCancel(true)

            notificationManager.notify(
                notificationId,
                buildNotification(baseBuilder)
                    .build()
            )
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        }
    }

    abstract class AbstractReminder(
        private val notificationId: Int,
        private val title: String,
        private val content: String,
    ) : Abstract(
        notificationId,
        R.string.reminders_channel_id
    ) {
        override fun pendingIntent(context: Context): PendingIntent {
            return CreatePendingIntent.MainActivity(context)
                .create(notificationId)
        }

        override fun buildNotification(base: Builder): Builder {
            return base.setCategory(CATEGORY_REMINDER)
                .setContentTitle(title)
                .setContentText(content)
        }
    }


    data class Reminder(
        private val notificationId: Int,
        private val title: String,
        private val content: String,
    ) : AbstractReminder(notificationId, title, content)


    data class ReminderMaxPriority(
        private val notificationId: Int,
        private val title: String,
        private val content: String,
    ) : AbstractReminder(notificationId, title, content) {
        override fun buildNotification(base: Builder): Builder =
            super.buildNotification(base)
                .setPriority(PRIORITY_MAX)
    }


    object Skip : BirthdayNotification {
        override fun notify(context: Context) = Unit
    }
}