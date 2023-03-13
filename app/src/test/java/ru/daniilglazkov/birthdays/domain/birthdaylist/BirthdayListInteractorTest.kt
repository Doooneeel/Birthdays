package ru.daniilglazkov.birthdays.domain.birthdaylist

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.TestCore
import ru.daniilglazkov.birthdays.domain.core.TestHandleRequest
import ru.daniilglazkov.birthdays.domain.birthday.BaseBirthdayTest
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.search.BirthdaySearch
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException
import java.time.LocalDate

class BirthdayListInteractorTest : BaseBirthdayTest(), TestCore {

    private lateinit var repository: TestBirthdayListRepository
    private lateinit var handleRequest: TestBirthdaysHandleDataRequest
    private lateinit var modifyBirthdayList: TestModifyBirthdayList
    private lateinit var birthdaySearch: TestBirthdaySearch

    private lateinit var interactor: BirthdayListInteractor

    @Before
    fun setUp() {
        repository = TestBirthdayListRepository()
        modifyBirthdayList = TestModifyBirthdayList()
        birthdaySearch = TestBirthdaySearch()
        handleRequest = TestBirthdaysHandleDataRequest()

        interactor = BirthdayListInteractor.Base(
            repository,
            modifyBirthdayList,
            birthdaySearch,
            handleRequest
        )
    }


    @Test
    fun test_birthdays_empty_query_success() = runBlocking {
        repository.data.add(BirthdayDomain.Mock("a"))
        repository.data.add(BirthdayDomain.Mock("b"))

        modifyBirthdayList.result = BirthdayListDomain.Base(
            BirthdayDomain.Base(0, "1", LocalDate.MAX)
        )

        val expected = BirthdayListResponse.Success(modifyBirthdayList.result)
        val actual: BirthdayListResponse = interactor.birthdays()

        assertEquals(expected, actual)

        assertEquals(1, modifyBirthdayList.calledList.size)
        assertEquals(BirthdayListDomain.Base(repository.data), modifyBirthdayList.calledList[0])

        assertEquals(1, repository.birthdaysCount)

        assertEquals(0, birthdaySearch.calledList.size)

        assertEquals(1, handleRequest.calledList.size)
        assertEquals(expected, handleRequest.calledList[0])
        assertEquals(0, handleRequest.exceptionList.size)
    }

    @Test
    fun test_birthdays_empty_query_failure() = runBlocking {
        repository.birthdayThrowsException = true

        handleRequest.result = BirthdayListResponse.Failure(EmptyCacheException())

        assertEquals(handleRequest.result, interactor.birthdays())
        assertEquals(handleRequest.result, interactor.birthdays("query"))

        assertEquals(2, repository.birthdaysCount)

        assertEquals(0, modifyBirthdayList.calledList.size)
        assertEquals(0, birthdaySearch.calledList.size)

        assertEquals(0, handleRequest.calledList.size)
        assertEquals(2, handleRequest.exceptionList.size)
        assertCollectionEquals(
            listOf(EmptyCacheException(), EmptyCacheException()),
            handleRequest.exceptionList
        )
    }

    @Test
    fun test_birthdays_not_empty_cache_with_query_success() = runBlocking {
        repository.data.add(BirthdayDomain.Mock("a"))
        repository.data.add(BirthdayDomain.Mock("b"))

        modifyBirthdayList.result = BirthdayListDomain.Base(
            BirthdayDomain.Base(0, "1", LocalDate.MAX)
        )

        birthdaySearch.result = BirthdayListDomain.Base(
            BirthdayDomain.Base(0, "found", LocalDate.MAX)
        )

        val expected = BirthdayListResponse.Success(modifyBirthdayList.result)
        val actual: BirthdayListResponse = interactor.birthdays("query")

        assertEquals(expected, actual)

        assertEquals(1, repository.birthdaysCount)

        assertEquals(1, birthdaySearch.calledList.size)
        assertEquals(
            Pair(BirthdayListDomain.Base(repository.data), "query"),
            birthdaySearch.calledList[0]
        )

        assertEquals(1, modifyBirthdayList.calledList.size)
        assertEquals(birthdaySearch.result, modifyBirthdayList.calledList[0])

        assertEquals(1, handleRequest.calledList.size)
        assertEquals(1, handleRequest.callCount)
        assertEquals(expected, handleRequest.calledList[0])
        assertEquals(0, handleRequest.exceptionList.size)
    }

    @Test
    fun test_birthdays_not_empty_cache_with_query_failure() = runBlocking {
        repository.data.add(BirthdayDomain.Mock("a"))
        repository.data.add(BirthdayDomain.Mock("b"))

        handleRequest.result = BirthdayListResponse.Failure(NotFoundException())

        val actual = interactor.birthdays("query")

        assertEquals(handleRequest.result, actual)

        assertEquals(1, repository.birthdaysCount)

        assertEquals(0, modifyBirthdayList.calledList.size)

        assertEquals(1, birthdaySearch.calledList.size)
        assertEquals(
            Pair(BirthdayListDomain.Base(repository.data), "query"),
            birthdaySearch.calledList[0]
        )

        assertEquals(1, handleRequest.callCount)
        assertEquals(1, handleRequest.exceptionList.size)
        assertEquals(NotFoundException(), handleRequest.exceptionList[0])
    }

    @Test
    fun test_first_launch() {
        repository.firstLaunchResult = true
        assertTrue(interactor.firstLaunch())

        repository.firstLaunchResult = false
        assertFalse(interactor.firstLaunch())

        assertEquals(2, repository.firstLaunchCallCount)
    }

    @Test
    fun test_determine_position() = runBlocking {
        val birthday = TestBirthdayList()
        birthday.positionResult = 10

        modifyBirthdayList.result = birthday

        assertEquals(-1, interactor.position(id = 10))
        assertEquals(-1, interactor.position(id = 1))
        assertEquals(-1, interactor.position(id = 0))
        assertEquals(-1, interactor.position(id = -1))

        interactor.birthdays()

        assertEquals(birthday.positionResult, interactor.position(0))
        assertEquals(birthday.positionResult, interactor.position(-1))
        assertEquals(birthday.positionResult, interactor.position(1))

        assertCollectionEquals(listOf(0, -1, 1), birthday.positionCalledList)
    }


    private class TestBirthdayList : BirthdayListDomain {

        var positionResult: Int = 0
        var positionCalledList = mutableListOf<Int>()

        override fun position(id: Int): Int {
            positionCalledList.add(id)
            return positionResult
        }

        override fun asList() = throw IllegalStateException("not used here")

        override fun <T> map(mapper: BirthdayListDomain.Mapper<T>): T {
            throw IllegalStateException("not used here")
        }
    }

    private class TestModifyBirthdayList : ModifyBirthdayList {

        val calledList = mutableListOf<BirthdayListDomain>()
        lateinit var result: BirthdayListDomain

        override suspend fun modify(source: BirthdayListDomain): BirthdayListDomain {
            calledList.add(source)
            return result
        }
    }

    private class TestBirthdaySearch : BirthdaySearch {

        val calledList = mutableListOf<Pair<BirthdayListDomain, CharSequence>>()
        var result: BirthdayListDomain? = null

        override fun search(source: BirthdayListDomain, query: CharSequence): BirthdayListDomain {
            calledList.add(Pair(source, query))
            return result ?: throw NotFoundException()
        }
    }

    private class TestBirthdaysHandleDataRequest : TestHandleRequest<BirthdayListResponse>(),
        HandleBirthdayListDataRequest
}