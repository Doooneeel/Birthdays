package ru.daniilglazkov.birthdays.data.newbirthday

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.data.BaseDataTest
import ru.daniilglazkov.birthdays.data.newbirthday.cache.NewBirthdayCacheDataSource
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import ru.daniilglazkov.birthdays.domain.newbirthday.NewBirthdayDomain
import java.time.LocalDate

class NewBirthdayRepositoryTest : BaseDataTest() {

    private lateinit var dataSource: TestCacheDataSource
    private lateinit var repository: BaseNewBirthdayRepository

    @Before
    fun setUp() {
        dataSource = TestCacheDataSource()
        repository = BaseNewBirthdayRepository(
            dataSource,
            NewBirthdayDataToDomainMapper.Base(),
            NewBirthdayDomainToDataMapper.Base()
        )
    }


    @Test
    fun test_save_to_cache() = runBlocking {
        val firstDate = LocalDate.of(2002, 3, 12)
        val secondData = LocalDate.of(2033, 1, 5)

        repository.save(NewBirthdayDomain.Base("b", firstDate))
        repository.save(NewBirthdayDomain.Base("c", secondData))

        assertEquals(2, dataSource.saveCallList.size)
        assertEquals(
            NewBirthdayData.Base("b", firstDate.toEpochDay()),
            dataSource.saveCallList[0]
        )
        assertEquals(
            NewBirthdayData.Base("c", secondData.toEpochDay()),
            dataSource.saveCallList[1]
        )
    }

    @Test
    fun test_new_birthday_success() = runBlocking {
        val date = LocalDate.MAX
        dataSource.data = NewBirthdayData.Base("a", date.toEpochDay())

        val expected = NewBirthdayDomain.Base("a", date)
        val actual: NewBirthdayDomain = repository.read()

        assertEquals(expected, actual)
        assertEquals(1, dataSource.newBirthdayCallCount)
    }

    @Test
    fun test_new_birthday_empty_cache() {
        assertThrowInBlock(EmptyCacheException::class.java) {
            runBlocking { repository.read() }
        }
        assertEquals(1, dataSource.newBirthdayCallCount)
    }


    private class TestCacheDataSource : NewBirthdayCacheDataSource {
        var data: NewBirthdayData? = null
        var newBirthdayCallCount = 0
        var saveCallList = mutableListOf<NewBirthdayData>()

        override suspend fun saveToCache(birthday: NewBirthdayData) { saveCallList.add(birthday) }

        override suspend fun newBirthday(): NewBirthdayData {
            ++newBirthdayCallCount
            return data ?: throw EmptyCacheException()
        }
    }
}