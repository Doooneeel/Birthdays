package ru.daniilglazkov.birthdays.ui.newbirthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.newbirthday.*
import ru.daniilglazkov.birthdays.ui.BaseUiTest
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.text.filter.TextFilter
import ru.daniilglazkov.birthdays.ui.newbirthday.about.*
import ru.daniilglazkov.birthdays.ui.newbirthday.validation.NewBirthdayValidateMapper
import ru.daniilglazkov.birthdays.ui.newbirthday.validation.NewBirthdayValidationResult
import java.time.LocalDate

/**
 * @author Danil Glazkov on 05.02.2023, 8:31
 */
@ExperimentalCoroutinesApi
class NewBirthdayViewModelTest : BaseUiTest() {

    private lateinit var interactor: TestInteractor
    private lateinit var sheetCommunication: TestSheetCommunication
    private lateinit var aboutBirthdateDomainToUiMapper: TestAboutBirthdateDomainToUiMapper
    private lateinit var communications: TestNewBirthdayCommunications
    private lateinit var validate: TestValidate
    private lateinit var filter: TestFilter
    private lateinit var dispatchers: TestDispatchers
    private lateinit var handleRequest: TestHandleNewBirthdayRequest

    private lateinit var viewModel: NewBirthdayViewModel


    @Before
    fun setUp() {
        interactor = TestInteractor()
        communications = TestNewBirthdayCommunications()
        sheetCommunication = TestSheetCommunication()
        handleRequest = TestHandleNewBirthdayRequest()
        aboutBirthdateDomainToUiMapper = TestAboutBirthdateDomainToUiMapper()
        validate = TestValidate()
        filter = TestFilter()
        dispatchers = TestDispatchers()

        viewModel = NewBirthdayViewModel.Base(
            interactor,
            communications,
            sheetCommunication,
            NewBirthdayUiToDomainMapper.Base(),
            validate,
            handleRequest,
            filter,
            dispatchers
        )
    }

    @Test
    fun test_clear_error_message() {
        viewModel.hideErrorMessage()

        assertEquals(1, communications.clearErrorMessageCallCount)
    }

    @Test
    fun test_clear_new_birthday() {
        viewModel.clearNewBirthday()

        assertEquals(1, communications.clearNewBirthdayCallCount)
    }

    @Test
    fun test_change_birthday() {
        val date = LocalDate.MAX
        val name = "a"

        filter.result = "filter result"

        viewModel.changeBirthday(name, date)

        assertEquals(1, filter.calledList.size)
        assertEquals(name, filter.calledList[0])
        assertEquals(1, communications.showNewBirthdayCalledList.size)
        assertEquals(
            NewBirthdayUi.Base(filter.result, date),
            communications.showNewBirthdayCalledList[0]
        )
        assertEquals(1, dispatchers.launchBackgroundCallCount)
        assertEquals(1, interactor.saveToCacheCalledList.size)
        assertEquals(
            NewBirthdayDomain.Base(filter.result, date),
            interactor.saveToCacheCalledList[0]
        )
    }

    @Test
    fun test_create_success() {
        val name = "a"
        val date = LocalDate.MIN

        communications.validateResult = NewBirthdayValidationResult.Valid(
            NewBirthdayUi.Base(name, date)
        )
        viewModel.create()

        assertEquals(1, dispatchers.launchBackgroundCallCount)

        assertEquals(1, interactor.createCalledList.size)
        assertEquals(NewBirthdayDomain.Base(name, date), interactor.createCalledList[0])

        assertEquals(1, communications.clearNewBirthdayCallCount)
        assertEquals(1, sheetCommunication.mapCalledList.size)
    }

    @Test
    fun test_create_invalid() {
        val errorMessage = ErrorMessage.Base("error")

        communications.validateResult = NewBirthdayValidationResult.Invalid(errorMessage)

        viewModel.create()

        assertEquals(1, communications.showErrorCalledList.size)
        assertEquals(errorMessage, communications.showErrorCalledList[0])

        assertEquals(0, dispatchers.launchBackgroundCallCount)
        assertEquals(0, interactor.createCalledList.size)
        assertEquals(0, communications.clearNewBirthdayCallCount)
        assertEquals(0, sheetCommunication.mapCalledList.size)
    }

    @Test
    fun test_fetch() {
        interactor.latestBirthday = NewBirthdayDomain.Base("a", LocalDate.MAX)

        viewModel.fetch()

        assertEquals(1, interactor.latestBirthdayCallCount)

        assertEquals(1, handleRequest.handleCallCount)
        assertEquals(interactor.latestBirthday, handleRequest.result)
    }

    @Test
    fun test_change_date() {
        val date = LocalDate.MAX
        interactor.aboutBirthdate = AboutBirthdateDomain.Base(123, 321)

        viewModel.changeDate(date)

        assertEquals(1, interactor.aboutBirthdateCalledList.size)
        assertEquals(date, interactor.aboutBirthdateCalledList[0])

        assertEquals(1, handleRequest.handleBirthdateCallCount)
        assertEquals(interactor.aboutBirthdate, handleRequest.aboutBirthdateResult)
    }


    private class TestValidate : NewBirthdayValidateMapper {

        lateinit var result: NewBirthdayValidationResult
        val calledList = mutableListOf<NewBirthdayUi>()

        override fun map(name: String, date: LocalDate): NewBirthdayValidationResult {
            calledList.add(NewBirthdayUi.Base(name, date))
            return result
        }
    }

    private class TestFilter : TextFilter {

        val calledList = mutableListOf<String>()
        var result: String = ""

        override fun filter(text: String): String {
            calledList.add(text)
            return result
        }
    }

    private class TestInteractor : NewBirthdayInteractor {

        lateinit var aboutBirthdate: AboutBirthdateDomain
        lateinit var latestBirthday: NewBirthdayDomain

        val aboutBirthdateCalledList = mutableListOf<LocalDate>()
        val saveToCacheCalledList = mutableListOf<NewBirthdayDomain>()
        val createCalledList = mutableListOf<NewBirthdayDomain>()
        var latestBirthdayCallCount = 0

        override suspend fun createNewBirthday(birthday: NewBirthdayDomain) {
            createCalledList.add(birthday)
        }

        override suspend fun latestBirthday(): NewBirthdayDomain {
            ++latestBirthdayCallCount
            return latestBirthday
        }

        override suspend fun saveToCache(newBirthday: NewBirthdayDomain) {
            saveToCacheCalledList.add(newBirthday)
        }

        override fun aboutBirthdate(date: LocalDate): AboutBirthdateDomain {
            aboutBirthdateCalledList.add(date)
            return aboutBirthdate
        }
    }

    private class TestHandleNewBirthdayRequest : TestHandleDomainRequest<NewBirthdayDomain>(),
        HandleNewBirthdayRequest {

        lateinit var aboutBirthdateResult: AboutBirthdateDomain
        var handleBirthdateCallCount = 0

        override fun handleBirthdate(
            scope: CoroutineScope,
            block: suspend () -> AboutBirthdateDomain
        ) {
            ++handleBirthdateCallCount
            runBlocking { aboutBirthdateResult = block.invoke() }
        }
    }

    private class TestNewBirthdayCommunications : NewBirthdayCommunications.Mutable {

        lateinit var validateResult: NewBirthdayValidationResult

        var clearErrorMessageCallCount = 0
        var clearNewBirthdayCallCount = 0

        val showErrorCalledList = mutableListOf<ErrorMessage>()
        val showAboutBirthdateCalledList = mutableListOf<AboutBirthdateUi>()
        val showNewBirthdayCalledList = mutableListOf<NewBirthdayUi>()
        val validateCalledList = mutableListOf<NewBirthdayValidateMapper>()

        override fun putNewBirthday(birthday: NewBirthdayUi) {
            showNewBirthdayCalledList.add(birthday)
        }

        override fun putAboutBirthdate(about: AboutBirthdateUi) {
            showAboutBirthdateCalledList.add(about)
        }

        override fun putError(message: ErrorMessage) {
            showErrorCalledList.add(message)
        }

        override fun validateNewBirthday(mapper: NewBirthdayValidateMapper): NewBirthdayValidationResult {
            validateCalledList.add(mapper)
            return validateResult
        }

        override fun hideErrorMessage() { ++clearErrorMessageCallCount }

        override fun clearNewBirthday() { ++clearNewBirthdayCallCount }

        override fun observeAboutBirthday(owner: LifecycleOwner, observer: Observer<AboutBirthdateUi>) = Unit

        override fun observeNewBirthday(owner: LifecycleOwner, observer: Observer<NewBirthdayUi>) = Unit

        override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>) = Unit
    }

    private class TestAboutBirthdateDomainToUiMapper : AboutBirthdateDomainToUiMapper {
        override fun map(turnsYearsOld: Int, daysToBirthday: Int) = AboutBirthdateUi.Base(
            turnsYearsOld.toString(),
            daysToBirthday.toString()
        )
    }
}