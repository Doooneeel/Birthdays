package ru.daniilglazkov.birthdays.domain.newbirthday

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.core.TestHandleRequest
import ru.daniilglazkov.birthdays.domain.birthday.BaseBirthdayTest
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import java.time.LocalDate

class NewBirthdayInteractorTest : BaseBirthdayTest() {

    private val testNow = LocalDate.MAX

    private lateinit var newBirthdayRepository: TestNewBirthdayRepository
    private lateinit var birthdayListRepository: TestBirthdayListRepository
    private lateinit var ageDateDifference: DateDifference.Test
    private lateinit var daysToBirthdayDateDifference: DateDifference.Test
    private lateinit var handleRequest: TestNewBirthdayHandleRequest

    private lateinit var newBirthdayInteractor: NewBirthdayInteractor


    @Before
    fun setUp() {
        newBirthdayRepository = TestNewBirthdayRepository()
        birthdayListRepository = TestBirthdayListRepository()
        ageDateDifference = DateDifference.Test()
        daysToBirthdayDateDifference = DateDifference.Test()
        handleRequest = TestNewBirthdayHandleRequest()

        newBirthdayInteractor = NewBirthdayInteractor.Base(
            birthdayListRepository,
            newBirthdayRepository,
            handleRequest,
            ageDateDifference,
            daysToBirthdayDateDifference
        )
    }

    @Test
    fun test_save_to_cache() = runBlocking {
        val firstBirthday = NewBirthdayDomain.Base("a", LocalDate.MIN)
        val secondBirthday = NewBirthdayDomain.Empty(testNow)

        newBirthdayInteractor.saveToCache(firstBirthday)
        newBirthdayInteractor.saveToCache(secondBirthday)

        assertEquals(2, newBirthdayRepository.saveToCacheCalledList.size)

        assertEquals(firstBirthday, newBirthdayRepository.saveToCacheCalledList[0])
        assertEquals(secondBirthday, newBirthdayRepository.saveToCacheCalledList[1])
    }

    @Test
    fun test_latest_new_birthday() = runBlocking {
        newBirthdayRepository.data = NewBirthdayDomain.Base("a", LocalDate.MAX)

        val expected = newBirthdayRepository.data
        val actual = newBirthdayInteractor.latestBirthday()

        assertEquals(expected, actual)
        assertEquals(1, newBirthdayRepository.newBirthdayCalledCount)

        assertEquals(1, handleRequest.calledList.size)
        assertEquals(expected, handleRequest.calledList[0])
    }

    @Test
    fun test_latest_new_birthday_empty_cache() = runBlocking {
        val expected = NewBirthdayDomain.Empty(testNow)
        handleRequest.result = expected

        val actual = newBirthdayInteractor.latestBirthday()

        assertEquals(expected, actual)
        assertEquals(1, newBirthdayRepository.newBirthdayCalledCount)
        assertEquals(1, handleRequest.callCount)
        assertEquals(1, handleRequest.exceptionList.size)
        assertEquals(EmptyCacheException(), handleRequest.exceptionList[0])
    }

    @Test
    fun test_about_birthdate() {
        ageDateDifference.result = 100
        daysToBirthdayDateDifference.result = 500

        val expected = AboutBirthdateDomain.Base(100, 500)
        val actual = newBirthdayInteractor.aboutBirthdate(LocalDate.MAX)

        assertEquals(expected, actual)

        assertEquals(1, ageDateDifference.calledList.size)
        assertEquals(LocalDate.MAX, ageDateDifference.calledList[0])

        assertEquals(1, daysToBirthdayDateDifference.calledList.size)
        assertEquals(LocalDate.MAX, daysToBirthdayDateDifference.calledList[0])
    }

    @Test
    fun test_create_new_birthday() = runBlocking {
        newBirthdayInteractor.createNewBirthday(NewBirthdayDomain.Base("a", LocalDate.MIN))

        assertEquals(1, birthdayListRepository.addCalledList.size)
        assertEquals(
            BirthdayDomain.Base(-1, "a", LocalDate.MIN),
            birthdayListRepository.addCalledList[0]
        )
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