package ru.daniilglazkov.birthdays.ui.birthdaylist

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.ui.BaseUiTest
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.BirthdayListDomainToChipsMapper
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.ChipListUi
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.ChipUi
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state.BirthdayListRecyclerState
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state.RecyclerState
import ru.daniilglazkov.birthdays.ui.core.HandleError
import ru.daniilglazkov.birthdays.ui.core.view.recycler.ScrollMode
import java.time.LocalDate

/**
 * @author Danil Glazkov on 04.03.2023, 13:58
 */
class BirthdayListResponseMapperTest : BaseUiTest() {

    private lateinit var communications: TestBirthdayListCommunications
    private lateinit var birthdayListDomainToItemsUiMapper: TestBirthdayListDomainToItemsUiMapper
    private lateinit var birthdayListDomainToChipsMapper: TestBirthdayListDomainToChipsMapper
    private lateinit var recyclerState: TestRecyclerState
    private lateinit var handleError: TestHandleError

    private lateinit var responseMapper: BirthdayListResponseMapper


    @Before
    fun setUp() {
        communications = TestBirthdayListCommunications()
        birthdayListDomainToItemsUiMapper = TestBirthdayListDomainToItemsUiMapper()
        birthdayListDomainToChipsMapper = TestBirthdayListDomainToChipsMapper()
        handleError = TestHandleError()
        recyclerState = TestRecyclerState()

        responseMapper = BirthdayListResponseMapper.Base(
            communications,
            birthdayListDomainToItemsUiMapper,
            birthdayListDomainToChipsMapper,
            recyclerState,
            handleError
        )
    }

    @Test
    fun test_success() {
        birthdayListDomainToItemsUiMapper.result = BirthdayItemUiList.Base(listOf(
            BirthdayItemUi.Header(-1, "a"),
            BirthdayItemUi.Header(-2, "b"),
            BirthdayItemUi.Header(-3, "c"),
        ))

        birthdayListDomainToChipsMapper.result = ChipListUi.Base(listOf(
            ChipUi.Base(0, "a"),
            ChipUi.Base(1, "b"),
        ))

        recyclerState.result = RecyclerState.Base(ScrollMode.SharpUp, false)

        val birthdayListDomain = BirthdayListDomain.Base(listOf(
            BirthdayDomain.Base(0, "name 1", LocalDate.MIN),
            BirthdayDomain.Base(1, "name 2", LocalDate.MAX),
            BirthdayDomain.Base(2, "name 3", LocalDate.MAX),
        ))

        responseMapper.map(birthdayListDomain)


        assertEquals(1, birthdayListDomainToItemsUiMapper.calledList.size)
        assertEquals(birthdayListDomain, birthdayListDomainToItemsUiMapper.calledList[0])

        assertEquals(1, birthdayListDomainToChipsMapper.calledList.size)
        assertEquals(birthdayListDomain, birthdayListDomainToChipsMapper.calledList[0])

        assertEquals(1, communications.putBirthdaysCalledList.size)
        assertEquals(
            birthdayListDomainToItemsUiMapper.result,
            communications.putBirthdaysCalledList[0]
        )

        assertEquals(1, communications.putChipsCalledList.size)
        assertEquals(
            birthdayListDomainToChipsMapper.result,
            communications.putChipsCalledList[0]
        )

        assertEquals(1, communications.putRecyclerStateCalledList.size)
        assertEquals(recyclerState.result, communications.putRecyclerStateCalledList[0])

        assertEquals(1, recyclerState.newStateCalledList.size)
        assertEquals(birthdayListDomain, recyclerState.newStateCalledList[0])
    }


    @Test
    fun test_exception() {
        handleError.result = "error"
        val exception = IllegalStateException()

        responseMapper.map(exception)


        assertEquals(1, communications.putBirthdaysCalledList.size)
        assertEquals(
            BirthdayItemUiList.Message("error"),
            communications.putBirthdaysCalledList[0]
        )

        assertEquals(1, handleError.calledList.size)
        assertEquals(exception, handleError.calledList[0])

        assertEquals(1, communications.putRecyclerStateCalledList.size)
        assertEquals(RecyclerState.Disable, communications.putRecyclerStateCalledList[0])

        assertEquals(1, communications.putChipsCalledList.size)
        assertEquals(ChipListUi.Empty, communications.putChipsCalledList[0])
    }


    private class TestRecyclerState : BirthdayListRecyclerState {

        val newStateCalledList = mutableListOf<BirthdayListDomain>()
        lateinit var result: RecyclerState

        override fun newState(changedList: BirthdayListDomain): RecyclerState {
            newStateCalledList.add(changedList)
            return result
        }
    }

    private class TestBirthdayListDomainToItemsUiMapper : BirthdayListDomainToItemsUiMapper {

        val calledList = mutableListOf<BirthdayListDomain>()
        lateinit var result: BirthdayItemUiList

        override fun map(list: List<BirthdayDomain>): BirthdayItemUiList {
            calledList.add(BirthdayListDomain.Base(list))
            return result
        }
    }

    private class TestBirthdayListDomainToChipsMapper : BirthdayListDomainToChipsMapper {

        val calledList = mutableListOf<BirthdayListDomain>()
        lateinit var result: ChipListUi

        override fun map(list: List<BirthdayDomain>): ChipListUi {
            calledList.add(BirthdayListDomain.Base(list))
            return result
        }
    }

    private class TestHandleError : HandleError {

        val calledList = mutableListOf<Exception>()
        lateinit var result: String

        override fun handle(exception: Exception): String {
            calledList.add(exception)
            return result
        }
    }
}