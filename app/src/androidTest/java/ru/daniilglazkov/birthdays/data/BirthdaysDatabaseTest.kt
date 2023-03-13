package ru.daniilglazkov.birthdays.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.daniilglazkov.birthdays.data.birthdaylist.cache.BirthdayCache
import ru.daniilglazkov.birthdays.data.birthdaylist.cache.BirthdaysDao
import ru.daniilglazkov.birthdays.data.core.cache.BirthdaysDatabase
import ru.daniilglazkov.birthdays.data.newbirthday.cache.NewBirthdayCache
import ru.daniilglazkov.birthdays.data.newbirthday.cache.NewBirthdayDao
import ru.daniilglazkov.birthdays.data.settings.cache.SettingsCache
import ru.daniilglazkov.birthdays.data.settings.cache.SettingsDao
import java.io.IOException

/**
 * @author Danil Glazkov on 14.02.2023, 20:19
 */
@RunWith(AndroidJUnit4::class)
class BirthdaysDatabaseTest {

    private lateinit var room: BirthdaysDatabase
    private lateinit var birthdaysDao: BirthdaysDao
    private lateinit var newBirthdayDao: NewBirthdayDao
    private lateinit var settingsDao: SettingsDao

    @Before
    fun setUp() {
        room = ProvideTestDatabase(BirthdaysDatabase::class.java)
            .provideDatabase()

        birthdaysDao = room.provideBirthdaysDao()
        newBirthdayDao = room.provideNewBirthdayDao()
        settingsDao = room.provideSettingsDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() = room.close()


    @Test
    fun test_birthdays_insert() {
        val expected = emptyArray<BirthdayCache>()
        val actual = birthdaysDao.allBirthdays().toTypedArray()

        assertArrayEquals(expected, actual)

        val firstCache = BirthdayCache("a", 1)
        val secondCache = BirthdayCache("b", 4)
        val thirdCache = BirthdayCache("b", 10)

        birthdaysDao.insert(firstCache)
        birthdaysDao.insert(secondCache)
        birthdaysDao.insert(thirdCache)

        val actualBirthdays = birthdaysDao.allBirthdays().toTypedArray()

        assertEquals(1, actualBirthdays[0].id)
        assertEquals(2, actualBirthdays[1].id)
        assertEquals(3, actualBirthdays[2].id)

        assertArrayEquals(arrayOf(firstCache, secondCache, thirdCache), actualBirthdays)
    }

    @Test
    fun test_birthdays_delete() {
        val firstCache = BirthdayCache("a", 12)
        val secondCache = BirthdayCache("b", 43)
        val thirdCache = BirthdayCache("b", 33)

        birthdaysDao.delete(0)

        birthdaysDao.insert(firstCache) //id 1
        birthdaysDao.insert(secondCache) //id 2
        birthdaysDao.insert(thirdCache) //id 3

        //removal of non-existent
        birthdaysDao.delete(5)
        birthdaysDao.delete(-1)
        birthdaysDao.delete(4)


        birthdaysDao.delete(2)
        assertArrayEquals(arrayOf(firstCache, thirdCache), birthdaysDao.allBirthdays().toTypedArray())

        birthdaysDao.delete(1)
        assertArrayEquals(arrayOf(thirdCache), birthdaysDao.allBirthdays().toTypedArray())

        birthdaysDao.delete(3)
        assertArrayEquals(arrayOf<BirthdayCache>(), birthdaysDao.allBirthdays().toTypedArray())
    }

    @Test
    fun test_birthdays_find() {
        assertNull(birthdaysDao.find(0))

        val firstCache = BirthdayCache("b", 40)
        val secondCache = BirthdayCache("a", 41)
        val thirdCache = BirthdayCache("c", 45)

        birthdaysDao.insert(firstCache)
        birthdaysDao.insert(secondCache)
        birthdaysDao.insert(thirdCache)

        assertNull(birthdaysDao.find(4))
        assertNull(birthdaysDao.find(-1))
        assertNull(birthdaysDao.find(0))

        assertEquals(firstCache, birthdaysDao.find(1))
        assertEquals(secondCache, birthdaysDao.find(2))
        assertEquals(thirdCache, birthdaysDao.find(3))
    }

    @Test
    fun test_new_birthday() {
        val firstCache = NewBirthdayCache("a", 10)

        assertEquals(null, newBirthdayDao.newBirthday())

        newBirthdayDao.insert(firstCache)

        val firstActual = newBirthdayDao.newBirthday()

        assertEquals(firstCache, firstActual)
        assertEquals(0, firstActual!!.id)


        val secondCache = NewBirthdayCache("c", 20)

        newBirthdayDao.insert(secondCache)

        val secondActual = newBirthdayDao.newBirthday()

        assertEquals(secondCache, secondActual)
        assertEquals(0, secondActual!!.id)
    }

    @Test
    fun test_settings() {
        val firstSettings = SettingsCache(1, reverse = false, group = true)

        assertEquals(null, settingsDao.settings())

        settingsDao.insert(firstSettings)

        val firstActual = settingsDao.settings()

        assertEquals(firstSettings, firstActual)
        assertEquals(0, firstActual!!.id)


        val secondSettings = SettingsCache(2, reverse = true, group = true)
        settingsDao.insert(secondSettings)

        val secondActual = settingsDao.settings()

        assertEquals(secondSettings, secondActual)
        assertEquals(0, secondActual!!.id)
    }
}