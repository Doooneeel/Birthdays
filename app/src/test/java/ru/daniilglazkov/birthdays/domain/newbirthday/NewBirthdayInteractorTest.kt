package ru.daniilglazkov.birthdays.domain.newbirthday

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.core.TestHandleRequest
import ru.daniilglazkov.birthdays.domain.birthday.BaseBirthdayTest
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import java.time.LocalDate

class NewBirthdayInteractorTest : BaseBirthdayTest() {

    private val testNow = LocalDate.MAX

    private lateinit var newBirthdayRepository: TestNewBirthdayRepository
    private lateinit var birthdayListRepository: TestBirthdayListRepository
    private lateinit var fetchDateOfBirthInfo: TestFetchDateOfBirthInfo
    private lateinit var handleRequest: TestNewBirthdayHandleRequest

    private lateinit var interactor: NewBirthdayInteractor


    @Before
    fun setUp() {
        newBirthdayRepository = TestNewBirthdayRepository()
        birthdayListRepository = TestBirthdayListRepository()
        fetchDateOfBirthInfo = TestFetchDateOfBirthInfo()
        handleRequest = TestNewBirthdayHandleRequest()

        interactor = NewBirthdayInteractor.Base(
            birthdayListRepository,
            newBirthdayRepository,
            handleRequest,
            fetchDateOfBirthInfo,
        )
    }

    @Test
    fun test_save_to_cache() = runBlocking {
        val firstBirthday = NewBirthdayDomain.Base("a", LocalDate.MIN)
        val secondBirthday = NewBirthdayDomain.Empty(testNow)

        interactor.saveToCache(firstBirthday)
        interactor.saveToCache(secondBirthday)

        assertEquals(2, newBirthdayRepository.saveToCacheCalledList.size)

        assertEquals(firstBirthday, newBirthdayRepository.saveToCacheCalledList[0])
        assertEquals(secondBirthday, newBirthdayRepository.saveToCacheCalledList[1])
    }

    @Test
    fun test_latest_new_birthday() = runBlocking {
        newBirthdayRepository.data = NewBirthdayDomain.Base("a", LocalDate.MAX)

        val expected = newBirthdayRepository.data
        val actual = interactor.latestBirthday()

        assertEquals(expected, actual)
        assertEquals(1, newBirthdayRepository.newBirthdayCalledCount)

        assertEquals(1, handleRequest.calledList.size)
        assertEquals(expected, handleRequest.calledList[0])
    }

    @Test
    fun test_latest_new_birthday_empty_cache() = runBlocking {
        val expected = NewBirthdayDomain.Empty(testNow)
        handleRequest.result = expected

        val actual = interactor.latestBirthday()

        assertEquals(expected, actual)
        assertEquals(1, newBirthdayRepository.newBirthdayCalledCount)
        assertEquals(1, handleRequest.callCount)
        assertEquals(1, handleRequest.exceptionList.size)
        assertEquals(EmptyCacheException(), handleRequest.exceptionList[0])
    }

    @Test
    fun test_about_birthdate() {
        fetchDateOfBirthInfo.result = DateOfBirthInfoDomain.Base(10, 20)

        interactor.dateOfBirthInfo(LocalDate.MIN)

        assertEquals(1, fetchDateOfBirthInfo.calledList.size)
        assertEquals(LocalDate.MIN, fetchDateOfBirthInfo.calledList[0])
    }

    @Test
    fun test_create_new_birthday() = runBlocking {
        interactor.createNewBirthday(NewBirthdayDomain.Base("a", LocalDate.MIN))

        assertEquals(1, birthdayListRepository.addCalledList.size)
        assertEquals(
            BirthdayDomain.Base(-1, "a", LocalDate.MIN),
            birthdayListRepository.addCalledList[0]
        )
    }


    private class TestFetchDateOfBirthInfo : FetchDateOfBirthInfo {

        lateinit var result: DateOfBirthInfoDomain
        val calledList = mutableListOf<LocalDate>()

        override fun fetchInfo(date: LocalDate): DateOfBirthInfoDomain {
            calledList.add(date)

            return result
        }
    }
    
    private open class TestNewBirthdayRepository : NewBirthdayRepository {

        var data: NewBirthdayDomain? = null
        var newBirthdayCalledCount = 0
        var saveToCacheCalledList = mutableListOf<NewBirthdayDomain>()

        override suspend fun read(): NewBirthdayDomain {
            ++newBirthdayCalledCount
            return data ?: throw EmptyCacheException()
        }
        override suspend fun save(data: NewBirthdayDomain) {
            saveToCacheCalledList.add(data)
        }
    }
    
    private class TestNewBirthdayHandleRequest : TestHandleRequest<NewBirthdayDomain>(),
        HandleNewBirthdayDataRequest
}