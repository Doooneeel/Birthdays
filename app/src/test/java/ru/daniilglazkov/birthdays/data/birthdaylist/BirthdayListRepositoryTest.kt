package ru.daniilglazkov.birthdays.data.birthdaylist

import android.content.res.Resources.NotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.TestCore
import ru.daniilglazkov.birthdays.data.birthdaylist.cache.BirthdayDataToDomainMapper
import ru.daniilglazkov.birthdays.data.birthdaylist.cache.BirthdayListCacheDataSource
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.core.FirstLaunch
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import java.time.LocalDate

class BirthdayListRepositoryTest : TestCore {

    private lateinit var dataSource: TestDataSource
    private lateinit var firstLaunch: TestFirstLaunch

    private lateinit var repository: BaseBirthdayListRepository

    @Before
    fun setUp() {
        dataSource = TestDataSource()
        firstLaunch = TestFirstLaunch()

        repository = BaseBirthdayListRepository(
            dataSource,
            BirthdayDataToDomainMapper.Base(),
            BirthdayDomainToDataMapper.Base(),
            firstLaunch
        )
    }


    @Test
    fun test_add() = runBlocking {
        val firstDate = LocalDate.MIN
        val secondDate = LocalDate.MAX

        repository.add(BirthdayDomain.Base(0, "a", firstDate))
        repository.add(BirthdayDomain.Base(1, "b", secondDate))

        assertCollectionEquals(
            listOf(
                BirthdayData.Base(0, "a", firstDate.toEpochDay()),
                BirthdayData.Base(1, "b", secondDate.toEpochDay())
            ),
            dataSource.addBirthdayCalledList
        )
    }

    @Test
    fun test_birthdays() = runBlocking {
        dataSource.birthdaysResult = mutableListOf(
            BirthdayData.Base(1, "a", 1000),
            BirthdayData.Base(2, "b", 2000)
        )

        val expected = BirthdayListDomain.Base(listOf(
            BirthdayDomain.Base(1, "a", LocalDate.ofEpochDay(1000)),
            BirthdayDomain.Base(2, "b", LocalDate.ofEpochDay(2000))
        ))

        val actual: BirthdayListDomain = repository.birthdays()

        assertEquals(expected, actual)
        assertEquals(1, dataSource.birthdaysCallCount)
    }

    @Test
    fun test_data_source_throws_exception() {
        dataSource.birthdaysResult = null

        assertThrowInBlock(EmptyCacheException::class.java) {
            repository.birthdays()
        }

        assertEquals(1, dataSource.birthdaysCallCount)
    }

    @Test
    fun test_birthdays_empty_cache() = runBlocking {
        dataSource.birthdaysResult = mutableListOf()

        assertEquals(BirthdayListDomain.Base(), repository.birthdays())
        assertEquals(1, dataSource.birthdaysCallCount)
    }

    @Test
    fun test_find() = runBlocking {
        val id = 10
        dataSource.findResult = BirthdayData.Base(id, "a", 2000)

        val expected: BirthdayDomain = BirthdayDomain.Base(id, "a", LocalDate.ofEpochDay(2000))
        val actual: BirthdayDomain = repository.find(id)

        assertEquals(expected, actual)

        assertEquals(1, dataSource.findCalledList.size)
        assertEquals(id, dataSource.findCalledList[0])
    }

    @Test
    fun test_not_found() {
        val id = 10
        dataSource.findResult = null

        assertThrowInBlock(NotFoundException::class.java) {
            repository.find(id)
        }

        assertEquals(1, dataSource.findCalledList.size)
        assertEquals(id, dataSource.findCalledList[0])
    }

    @Test
    fun test_delete() = runBlocking {
        repository.delete(-1)
        repository.delete(0)
        repository.delete(2)

        repository.delete(Int.MAX_VALUE)
        repository.delete(Int.MIN_VALUE)

        assertCollectionEquals(
            listOf(-1, 0, 2, Int.MAX_VALUE, Int.MIN_VALUE),
            dataSource.deleteCalledList
        )
    }

    @Test
    fun test_first_launch() {
        firstLaunch.result = true
        assertTrue(repository.firstLaunch())
        assertTrue(repository.firstLaunch())

        assertEquals(firstLaunch.callCount, 2)

        firstLaunch.result = false
        assertFalse(repository.firstLaunch())
        assertFalse(repository.firstLaunch())

        assertEquals(firstLaunch.callCount, 4)
    }


    private class TestFirstLaunch(var result: Boolean = false) : FirstLaunch {
        var callCount = 0

        override fun firstLaunch(): Boolean {
            ++callCount
            return result
        }
    }

    private class TestDataSource : BirthdayListCacheDataSource {

        var findResult: BirthdayData? = null
        var birthdaysResult: MutableList<BirthdayData>? = mutableListOf()
        val deleteCalledList = mutableListOf<Int>()
        val findCalledList = mutableListOf<Int>()
        var birthdaysCallCount = 0
        val addBirthdayCalledList = mutableListOf<BirthdayData>()

        override suspend fun addBirthday(birthday: BirthdayData) {
            addBirthdayCalledList.add(birthday)
        }

        override suspend fun delete(id: Int) { deleteCalledList.add(id) }

        override suspend fun birthdays(): List<BirthdayData> {
            ++birthdaysCallCount
            return birthdaysResult ?: throw EmptyCacheException()
        }

        override suspend fun find(id: Int): BirthdayData {
            findCalledList.add(id)

            return findResult ?: throw NotFoundException()
        }
    }
}
