package ru.daniilglazkov.birthdays.ui.birthdaylist

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.*
import ru.daniilglazkov.birthdays.ui.BaseUiTest
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.*
import ru.daniilglazkov.birthdays.ui.newbirthday.NewBirthdayScreen
import ru.daniilglazkov.birthdays.ui.settings.SettingsScreen

/**
 * @author Danil Glazkov on 05.02.2023, 11:27
 */
class BirthdayListViewModelTest : BaseUiTest() {

    private lateinit var interactor: TestBirthdayListInteractor
    private lateinit var navigation: TestNavigation
    private lateinit var communications: TestBirthdayListCommunications
    private lateinit var handleRequest: TestHandleBirthdayListRequest

    private lateinit var viewModel: BirthdayListViewModel

    @Before
    fun setUp() {
        interactor = TestBirthdayListInteractor()
        navigation = TestNavigation()
        communications = TestBirthdayListCommunications()
        handleRequest = TestHandleBirthdayListRequest()

        viewModel = BirthdayListViewModel.Base(
            interactor,
            communications,
            handleRequest,
            navigation,
        )
    }

    @Test
    fun test_init() {
        interactor.firstLaunch = true

        viewModel.init(true)
        assertEquals(1, navigation.mapCalledList.size)

        interactor.firstLaunch = false
        viewModel.init(true)

        interactor.firstLaunch = true
        viewModel.init(false)

        interactor.firstLaunch = false
        viewModel.init(false)

        assertEquals(1, navigation.mapCalledList.size)
    }

    @Test
    fun test_fetch() {
        communications.query = "123"

        interactor.result = BirthdayListResponse.Success(
            BirthdayListDomain.Base(BirthdayDomain.Mock("a"))
        )

        viewModel.fetch()

        assertEquals(1, communications.fetchBirthdaysCallCount)

        assertEquals(1, interactor.birthdaysCalledList.size)
        assertEquals("123", interactor.birthdaysCalledList[0])

        assertEquals(interactor.result, handleRequest.result)
    }

    @Test
    fun test_change_search_query() {
        viewModel.changeSearchQuery("query")

        assertEquals(1, communications.putQueryCalledList.size)
        assertEquals("query", communications.putQueryCalledList[0])
    }

    @Test
    fun test_change_position() {
        val chipId = 5
        val position = 12

        interactor.positionResult = position

        viewModel.changePosition(ChipUi.Base(chipId, "a"))

        assertEquals(1, communications.smoothScrollCalledList.size)
        assertEquals(position, communications.smoothScrollCalledList[0])

        assertEquals(1, interactor.calculatePositionCalledList.size)
        assertEquals(chipId, interactor.calculatePositionCalledList[0])
    }

    @Test
    fun test_show_settings_dialog() {
        viewModel.showSettingsDialog()

        assertEquals(1, navigation.mapCalledList.size)
        assertEquals(SettingsScreen(), navigation.mapCalledList[0])
    }

    @Test
    fun test_show_new_birthday_dialog() {
        viewModel.showNewBirthdayDialog()

        assertEquals(1, navigation.mapCalledList.size)
        assertEquals(NewBirthdayScreen(), navigation.mapCalledList[0])
    }


    private class TestBirthdayListInteractor : BirthdayListInteractor {

        lateinit var result: BirthdayListResponse

        var positionResult: Int = 0
        val birthdaysCalledList = mutableListOf<CharSequence>()
        val calculatePositionCalledList = mutableListOf<Int>()
        var firstLaunch = false

        override suspend fun birthdays(query: CharSequence): BirthdayListResponse {
            birthdaysCalledList.add(query)
            return result
        }

        override fun firstLaunch(): Boolean = firstLaunch

        override fun position(id: Int): Int {
            calculatePositionCalledList.add(id)
            return positionResult
        }
    }

    private class TestHandleBirthdayListRequest : TestHandleDomainRequest<BirthdayListResponse>(),
        HandleBirthdayListRequest
}