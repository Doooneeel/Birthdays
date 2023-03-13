package ru.daniilglazkov.birthdays.ui.birthday

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.*
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacsDomain
import ru.daniilglazkov.birthdays.ui.BaseUiTest

/**
 * @author Danil Glazkov on 03.02.2023, 0:47
 */
@ExperimentalCoroutinesApi
class BirthdayViewModelTest : BaseUiTest() {

    private lateinit var interactor: TestBirthdayInteractor
    private lateinit var sheetCommunication: TestSheetCommunication
    private lateinit var handleRequest: TestsHandleRequest
    private lateinit var dispatchers: TestDispatchers
    private lateinit var communications: TestBirthdayCommunications

    private lateinit var viewModel: BirthdayViewModel

    @Before
    fun setUp() {
        communications = TestBirthdayCommunications()
        interactor = TestBirthdayInteractor()
        sheetCommunication = TestSheetCommunication()
        handleRequest = TestsHandleRequest()
        dispatchers = TestDispatchers()

        viewModel = BirthdayViewModel.Base(
            interactor,
            communications,
            sheetCommunication,
            handleRequest,
            dispatchers
        )
    }


    @Test
    fun test_init() {
        viewModel.init(false, 0)
        assertEquals(0, communications.putIdCalledList.size)

        viewModel.init(true, 10)
        assertEquals(1, communications.putIdCalledList.size)
        assertEquals(10, communications.putIdCalledList[0])


        viewModel.init(false, 20)
        assertEquals(1, communications.putIdCalledList.size)
        assertEquals(10, communications.putIdCalledList[0])
    }

    @Test
    fun test_change_delete_state() {
        viewModel.changeDeleteState(true)

        assertEquals(1, communications.putDeleteStateCalledList.size)
        assertEquals(true, communications.putDeleteStateCalledList[0])


        viewModel.changeDeleteState(false)

        assertEquals(2, communications.putDeleteStateCalledList.size)
        assertEquals(false, communications.putDeleteStateCalledList[1])
    }

    @Test
    fun test_complete_delete_state_is_true() {
        communications.deleteState = true
        communications.id = 10

        viewModel.complete()

        assertEquals(1, communications.handleTrueDeleteStateCallCount)
        assertEquals(1, dispatchers.launchBackgroundCallCount)
        assertEquals(1, interactor.deleteCalledList.size)
        assertEquals(1, communications.deleteCallCount)
        assertEquals(communications.id, interactor.deleteCalledList[0])
    }

    @Test
    fun test_complete_delete_state_is_false() {
        communications.deleteState = false
        communications.id = 10

        viewModel.complete()

        assertEquals(1, communications.handleTrueDeleteStateCallCount)
        assertEquals(0, dispatchers.launchBackgroundCallCount)
        assertEquals(0, communications.deleteCallCount)
        assertEquals(0, interactor.deleteCalledList.size)
    }

    @Test
    fun test_fetch() {
        val response = BirthdayResponse.Success(BirthdayDomain.Mock(), ZodiacsDomain.Mock())

        communications.id = 20
        interactor.findResult = response

        viewModel.fetch()


        assertEquals(1, communications.findCallCount)

        assertEquals(1, interactor.findCelledList.size)
        assertEquals(communications.id, interactor.findCelledList[0])

        assertEquals(1, handleRequest.handleCallCount)
        assertEquals(response, handleRequest.result)
    }


    private class TestBirthdayInteractor : BirthdayInteractor {

        val deleteCalledList = mutableListOf<Int>()
        val findCelledList = mutableListOf<Int>()

        lateinit var findResult: BirthdayResponse

        override suspend fun delete(id: Int) { deleteCalledList.add(id) }

        override suspend fun find(id: Int): BirthdayResponse {
            findCelledList.add(id)
            return findResult
        }
    }

    private class TestsHandleRequest : TestHandleDomainRequest<BirthdayResponse>(),
        HandleBirthdayRequest
}