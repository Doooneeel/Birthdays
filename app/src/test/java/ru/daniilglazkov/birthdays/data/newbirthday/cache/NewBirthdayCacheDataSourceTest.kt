package ru.daniilglazkov.birthdays.data.newbirthday.cache

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.data.BaseDataTest
import ru.daniilglazkov.birthdays.data.newbirthday.NewBirthdayData
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException

class NewBirthdayCacheDataSourceTest : BaseDataTest() {

    private lateinit var testDao: TestNewBirthdayDao
    private lateinit var dataSource: NewBirthdayCacheDataSource


    @Before
    fun setUp() {
        testDao = TestNewBirthdayDao()
        dataSource = NewBirthdayCacheDataSource.Base(
            testDao,
            NewBirthdayDataToCacheMapper.Base()
        )
    }


    @Test
    fun test_save_to_cache() = runBlocking {
        dataSource.saveToCache(NewBirthdayData.Base("a", 1))
        dataSource.saveToCache(NewBirthdayData.Base("b", 2))

        assertEquals(2, testDao.insertCalledList.size)

        assertEquals(NewBirthdayCache("a", 1), testDao.insertCalledList[0])
        assertEquals(NewBirthdayCache("b", 2), testDao.insertCalledList[1])
    }

    @Test
    fun test_new_birthday() = runBlocking {
        testDao.data = NewBirthdayCache("a", 1)

        val expected = NewBirthdayData.Base("a", 1)
        val actual = dataSource.newBirthday()

        assertEquals(expected, actual)
        assertEquals(1, testDao.newBirthdayCount)
    }

    @Test
    fun test_new_birthday_empty_cache() {
        assertThrowInBlock(EmptyCacheException::class.java) {
            dataSource.newBirthday()
        }

        assertEquals(1, testDao.newBirthdayCount)
    }

    private class TestNewBirthdayDao : TestAbstractDao<NewBirthdayCache>(), NewBirthdayDao {
        var newBirthdayCount = 0

        override fun newBirthday(): NewBirthdayCache {
            ++newBirthdayCount
            return data ?: throw EmptyCacheException()
        }
    }
}