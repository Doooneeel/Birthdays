package ru.daniilglazkov.birthdays.service.birthday.notification

import android.content.*
import kotlinx.coroutines.CoroutineExceptionHandler
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import ru.daniilglazkov.birthdays.service.core.receivers.AbstractReceiver
import ru.daniilglazkov.birthdays.sl.core.ThisApp

/**
 * @author Danil Glazkov on 14.03.2023, 0:36
 */
class NotificationReceiver : AbstractReceiver() {

    override val handlerCoroutineException = CoroutineExceptionHandler { _, exception ->
        if (exception !is EmptyCacheException) {
            exception.printStackTrace()
        }
    }

    override suspend fun handle(context: Context, intent: Intent) {
        val application = context.applicationContext as ThisApp

        val notificationMapper: BirthdayNotificationMapper = application.provideNotificationMapper()

        val birthdayList: List<BirthdayDomain> = application.provideBirthdaysRepository()
            .birthdays()
            .asList()

        val notifications: List<BirthdayNotification> = birthdayList.map { birthday ->
            birthday.map(notificationMapper)
        }

        notifications.forEach { notification: BirthdayNotification ->
            notification.notify(application)
        }
    }
}