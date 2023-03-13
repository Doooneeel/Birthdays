package ru.daniilglazkov.birthdays.data.birthdaylist.cache

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.data.birthdaylist.BirthdayData
import ru.daniilglazkov.birthdays.data.BaseDataTest
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException

class BirthdayListCacheDataSourceTest : BaseDataTest() {

    private lateinit var dao: TestBirthdaysDao
    private lateinit var dataSource: BirthdayListCacheDataSource

    @Before
    fun setUp() {
        dao = TestBirthdaysDao()
        dataSource = BirthdayListCacheDataSource.Base(dao, BirthdayDataToCacheMapper.Base())
    }


    @Test
    fun test_save() = runBlocking {
        dataSource.addBirthday(BirthdayData.Base(0, "a", 10))
        dataSource.addBirthday(BirthdayData.Base(1, "b", 20))

        assertCollectionEquals(
            listOf(BirthdayCache("a", 10), BirthdayCache("b", 20)),
            dao.insertCalledList
        )
    }

    @Test
    fun test_birthdays() = runBlocking {
        dao.birthdaysResult.add(BirthdayCache("a", 10).apply { id = 1 })
        dao.birthdaysResult.add(BirthdayCache("b", 20).apply { id = 2 })

        val expected = listOf(BirthdayData.Base(1, "a", 10), BirthdayData.Base(2, "b", 20))
        val actual = dataSource.birthdays()

        assertCollectionEquals(expected, actual)
        assertEquals(1, dao.birthdaysCallCount)
    }

    @Test
    fun test_birthdays_empty_cache() {
        assertThrowInBlock(EmptyCacheException::class.java) {
            dataSource.birthdays()
        }

        assertEquals(1, dao.birthdaysCallCount)
    }

    @Test
    fun test_find_success() = runBlocking {
        val id = 5
        dao.searchResults = BirthdayCache("a", 1000).also { it.id = id }

        assertEquals(BirthdayData.Base(id, "a", 1000), dataSource.find(id))

        assertEquals(1, dao.findCalledList.size)
        assertEquals(id, dao.findCalledList[0])
    }

    @Test
    fun test_not_found() {
        assertThrowInBlock(NotFoundException::class.java) {
            dataSource.find(10)
        }

        assertEquals(1, dao.findCalledList.size)
        assertEquals(10, dao.findCalledList[0])
    }

    @Test
    fun test_delete() = runBlocking {
        dataSource.delete(10)

        assertEquals(1, dao.deleteCalledList.size)
        assertEquals(10, dao.deleteCalledList[0])
    }


    private class TestBirthdaysDao : BirthdaysDao {

        var searchResults: BirthdayCache? = null
        var birthdaysResult: MutableList<BirthdayCache> = mutableListOf()
        val findCalledList = mutableListOf<Int>()
        val deleteCalledList = mutableListOf<Int>()
        val insertCalledList = mutableListOf<BirthdayCache>()
        var birthdaysCallCount = 0

        override fun allBirthdays(): List<BirthdayCache> {
            ++birthdaysCallCount
            return birthdaysResult
        }

        override fun find(id: Int): BirthdayCache? {
            findCalledList.add(id)
            return searchResults
        }

        override fun delete(id: Int) { deleteCalledList.add(id) }

        override fun insert(data: BirthdayCache) { insertCalledList.add(data) }
    }
}