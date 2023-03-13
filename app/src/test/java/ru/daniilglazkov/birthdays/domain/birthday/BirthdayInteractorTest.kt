package ru.daniilglazkov.birthdays.domain.birthday

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.core.TestHandleRequest
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException
import ru.daniilglazkov.birthdays.domain.zodiac.*
import ru.daniilglazkov.birthdays.domain.zodiac.chinese.ChineseZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacDomain
import java.time.LocalDate

class BirthdayInteractorTest : BaseBirthdayTest() {

    private lateinit var repository: TestBirthdayListRepository
    private lateinit var greekZodiacMapper: TestZodiacMapper
    private lateinit var chineseZodiacMapper: TestZodiacMapper
    private lateinit var handleRequest: TestBirthdayHandleDataRequest

    private lateinit var interactor: BirthdayInteractor

    @Before
    fun setUp() {
        repository = TestBirthdayListRepository()
        handleRequest = TestBirthdayHandleDataRequest()
        greekZodiacMapper = TestZodiacMapper()
        chineseZodiacMapper = TestZodiacMapper()

        interactor = BirthdayInteractor.Base(
            repository,
            handleRequest,
            greekZodiacMapper,
            chineseZodiacMapper
        )
    }


    @Test
    fun test_find_success() = runBlocking {
        repository.searchResult = BirthdayDomain.Mock()

        greekZodiacMapper.zodiac = GreekZodiacDomain.Base(1, "a")
        chineseZodiacMapper.zodiac = ChineseZodiacDomain.Base(2, "c")

        val expected = BirthdayResponse.Success(
            repository.searchResult!!,
            ZodiacsDomain.Base(
                greekZodiacMapper.zodiac,
                chineseZodiacMapper.zodiac
            )
        )
        val actual: BirthdayResponse = interactor.find(10)

        assertEquals(expected, actual)

        assertEquals(1, handleRequest.calledList.size)
        assertEquals(expected, handleRequest.calledList[0])

        assertEquals(1, repository.findCalledList.size)
        assertEquals(10, repository.findCalledList[0])
    }

    @Test
    fun test_not_found() = runBlocking {
        handleRequest.result = BirthdayResponse.Failure(IllegalStateException())

        assertEquals(handleRequest.result, interactor.find(10))

        assertEquals(1, handleRequest.callCount)
        assertEquals(1, handleRequest.exceptionList.size)
        assertEquals(NotFoundException(), handleRequest.exceptionList[0])

        assertEquals(1, repository.findCalledList.size)
        assertEquals(10, repository.findCalledList[0])
    }

    @Test
    fun test_delete() = runBlocking {
        val id = 10
        repository.data.add(BirthdayDomain.Base(id, "a", LocalDate.MIN))

        interactor.delete(id)

        assertEquals(1, repository.deleteCalledList.size)
        assertEquals(id, repository.deleteCalledList[0])
    }


    private class TestZodiacMapper : BirthdayZodiacMapper {
        lateinit var zodiac: ZodiacDomain

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) = zodiac
    }

    private class TestBirthdayHandleDataRequest : TestHandleRequest<BirthdayResponse>(),
        HandleBirthdayDataRequest
}