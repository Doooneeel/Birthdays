package ru.daniilglazkov.birthdays.service.birthday.notification

import ru.daniilglazkov.birthdays.data.core.cache.PreferencesDataStore

/**
 * @author Danil Glazkov on 23.03.2023, 07:45
 */
interface ManageNotificationDisplayStatus {

    fun fetchBasedOnStatus(
        targetNotification: BirthdayNotification,
        targetKey: String,
        allKeys: List<String>,
    ) : BirthdayNotification

    fun resetStatus(keys: List<String>)


    class Base(
        private val dataStore: PreferencesDataStore.Mutable<Boolean>,
    ) : ManageNotificationDisplayStatus {

        override fun fetchBasedOnStatus(
            targetNotification: BirthdayNotification,
            targetKey: String,
            allKeys: List<String>,
        ) : BirthdayNotification {
            val wasShown: Boolean = dataStore.read(targetKey, defaultValue = false)
            dataStore.save(targetKey, true)

            val keysWithoutTarget = ArrayList(allKeys)
            keysWithoutTarget.remove(targetKey)

            resetStatus(keysWithoutTarget)

            return if (wasShown) BirthdayNotification.Skip else targetNotification
        }

        override fun resetStatus(keys: List<String>) = keys.forEach { key ->
            dataStore.save(key, false)
        }
    }
}