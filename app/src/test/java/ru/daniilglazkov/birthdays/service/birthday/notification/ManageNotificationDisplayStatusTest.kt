package ru.daniilglazkov.birthdays.service.birthday.notification

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.data.BaseDataTest

/**
 * @author Danil Glazkov on 23.03.2023, 7:46
 */
class ManageNotificationDisplayStatusTest : BaseDataTest() {

    private lateinit var manageStatus: ManageNotificationDisplayStatus
    private lateinit var dataStore: TestDataStore<Boolean>

    private val skip = BirthdayNotification.Skip

    @Before
    fun setUp() {
        dataStore = TestDataStore()
        manageStatus = ManageNotificationDisplayStatus.Base(dataStore)
    }


    @Test
    fun test_fetch_status_true() {
        val targetNotification = BirthdayNotification.Reminder(123, "text", "content")
        val targetKey = "firstKey"
        val secondKey = "secondKey"
        val thirdKey = "thirdKey"
        val allKeys = listOf(targetKey, secondKey, thirdKey)

        dataStore.data[targetKey] = true
        val defaultValue = false

        assertEquals(
            BirthdayNotification.Skip,
            manageStatus.fetchBasedOnStatus(targetNotification, targetKey, allKeys)
        )

        assertCollectionEquals(
            listOf(Pair(targetKey, true), Pair(secondKey, false), Pair(thirdKey, false)),
            dataStore.saveCalledList
        )

        assertCollectionEquals(listOf(Pair(targetKey, defaultValue)), dataStore.readCalledList)

        assertEquals(
            BirthdayNotification.Skip,
            manageStatus.fetchBasedOnStatus(targetNotification, targetKey, allKeys)
        )
        assertCollectionEquals(
            listOf(
                Pair(targetKey, defaultValue),
                Pair(targetKey, defaultValue),
            ),
            dataStore.readCalledList
        )

        assertCollectionEquals(
            listOf(
                Pair(targetKey, true), Pair(secondKey, false), Pair(thirdKey, false),
                Pair(targetKey, true), Pair(secondKey, false), Pair(thirdKey, false)
            ),
            dataStore.saveCalledList
        )

        assertEquals(
            BirthdayNotification.Skip,
            manageStatus.fetchBasedOnStatus(targetNotification, targetKey, allKeys)
        )
    }

    @Test
    fun test_fetch_status_false() {
        val targetNotification = BirthdayNotification.Reminder(123, "text", "content")
        val targetKey = "firstKey"
        val secondKey = "secondKey"
        val thirdKey = "thirdKey"

        val allKeys = listOf(targetKey, secondKey, thirdKey)

        dataStore.data[targetKey] = false

        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, targetKey, allKeys)
        )

        val defaultValue = false

        assertCollectionEquals(listOf(Pair(targetKey, defaultValue)), dataStore.readCalledList)
        assertCollectionEquals(
            listOf(Pair(targetKey, true), Pair(secondKey, false), Pair(thirdKey, false)),
            dataStore.saveCalledList
        )

        assertEquals(
            BirthdayNotification.Skip,
            manageStatus.fetchBasedOnStatus(targetNotification, targetKey, allKeys)
        )
        assertCollectionEquals(
            listOf(
                Pair(targetKey, defaultValue),
                Pair(targetKey, defaultValue),
            ),
            dataStore.readCalledList
        )
        assertCollectionEquals(
            listOf(
                Pair(targetKey, true), Pair(secondKey, false), Pair(thirdKey, false),
                Pair(targetKey, true), Pair(secondKey, false), Pair(thirdKey, false)
            ),
            dataStore.saveCalledList
        )

        assertEquals(
            BirthdayNotification.Skip,
            manageStatus.fetchBasedOnStatus(targetNotification, targetKey, allKeys)
        )
    }

    @Test
    fun test_change_target_key() {
        val targetNotification = BirthdayNotification.Skip

        val firstKey = "firstKey"
        val secondKey = "secondKey"
        val thirdKey = "thirdKey"

        val allKeys = listOf(firstKey, secondKey, thirdKey)

        allKeys.forEach { key ->
            dataStore.data[key] = false
        }

        assertEquals(
            BirthdayNotification.Skip,
            manageStatus.fetchBasedOnStatus(targetNotification, firstKey, allKeys)
        )

        assertEquals(
            BirthdayNotification.Skip,
            manageStatus.fetchBasedOnStatus(targetNotification, thirdKey, allKeys)
        )

        assertEquals(
            BirthdayNotification.Skip,
            manageStatus.fetchBasedOnStatus(targetNotification, secondKey, allKeys)
        )

        assertEquals(
            BirthdayNotification.Skip,
            manageStatus.fetchBasedOnStatus(targetNotification, secondKey, allKeys)
        )

        assertCollectionEquals(
            listOf(
                Pair("firstKey", true), Pair("secondKey", false), Pair("thirdKey", false),
                Pair("thirdKey", true), Pair("firstKey", false), Pair("secondKey", false),
                Pair("secondKey", true), Pair("firstKey", false), Pair("thirdKey", false),
                Pair("secondKey", true), Pair("firstKey", false), Pair("thirdKey", false),
            ),
            dataStore.saveCalledList
        )

        dataStore.data[firstKey] = false
        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, firstKey, allKeys)
        )

        dataStore.data[secondKey] = false
        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, secondKey, allKeys)
        )

        dataStore.data[thirdKey] = false
        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, thirdKey, allKeys)
        )

        dataStore.data[firstKey] = false
        dataStore.data[secondKey] = true
        dataStore.data[thirdKey] = true

        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, firstKey, allKeys)
        )

        dataStore.data[firstKey] = true
        dataStore.data[secondKey] = true
        dataStore.data[thirdKey] = false

        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, thirdKey, allKeys)
        )

        dataStore.data[firstKey] = true
        dataStore.data[secondKey] = false
        dataStore.data[thirdKey] = true

        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, secondKey, allKeys)
        )

        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, firstKey, allKeys)
        )
        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, secondKey, allKeys)
        )
        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, thirdKey, allKeys)
        )
    }

    @Test
    fun test_repeating_calls() {
        val targetNotification = BirthdayNotification.Reminder(123, "a", "b")
        val targetKey = "targetKey"
        val key1 = "key1"
        val key2 = "key2"
        val allKeys = listOf(targetKey, key1, key2)

        dataStore.data[targetKey] = false
        dataStore.data[key1] = false
        dataStore.data[key2] = false

        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, targetKey, allKeys)
        )
        assertEquals(skip, manageStatus.fetchBasedOnStatus(targetNotification, targetKey, allKeys))
        assertEquals(skip, manageStatus.fetchBasedOnStatus(targetNotification, targetKey, allKeys))

        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, key1, allKeys)
        )
        assertEquals(skip, manageStatus.fetchBasedOnStatus(targetNotification, key1, allKeys))

        assertEquals(
            targetNotification,
            manageStatus.fetchBasedOnStatus(targetNotification, key2, allKeys)
        )
        assertEquals(skip, manageStatus.fetchBasedOnStatus(targetNotification, key2, allKeys))

        allKeys.forEach { key ->
            assertEquals(
                targetNotification,
                manageStatus.fetchBasedOnStatus(targetNotification, key, allKeys)
            )
        }
    }

    @Test
    fun test_reset_status() {
        manageStatus.resetStatus(listOf("a", "b", "c"))
        manageStatus.resetStatus(listOf("x"))
        manageStatus.resetStatus(listOf("h", "g"))

        assertCollectionEquals(
            listOf(
                Pair("a", false), Pair("b", false), Pair("c", false),
                Pair("x", false),
                Pair("h", false), Pair("g", false)
            ),
            dataStore.saveCalledList
        )
    }
}