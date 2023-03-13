package ru.daniilglazkov.birthdays.domain.birthdaylist

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.core.TestHandleRequest
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.TransformBirthdayList
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.TransformBirthdayListFactory
import ru.daniilglazkov.birthdays.domain.core.Repository
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import ru.daniilglazkov.birthdays.domain.settings.*
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode

/**
 * @author Danil Glazkov on 06.03.2023, 10:43
 */
class ModifyBirthdayListTest {

    private val birthdayList = BirthdayListDomain.Base(
        BirthdayDomain.Mock("a"),
        BirthdayDomain.Mock("b"),
        BirthdayDomain.Mock("c"),
    )

    private val transformedList = BirthdayListDomain.Base(
        BirthdayDomain.Header(1, "1"),
        BirthdayDomain.Header(2, "10"),
        BirthdayDomain.Mock("20"),
    )

    @Test
    fun test_modify() = runBlocking {
        val handleRequest = TestHandleSettingsDataRequest()
        val repository = TestSettingsRepository()
        val mapper = TestTransformBirthdayListFactory()

        val modifyBirthdayList = ModifyBirthdayList.Base(repository, handleRequest, mapper)

        repository.result = TestSettings()
        handleRequest.result = TestSettings()
        mapper.transformBirthdayList = TestTransformBirthdayList()
        mapper.transformBirthdayList.result = transformedList

        assertEquals(transformedList, modifyBirthdayList.modify(birthdayList))


        assertEquals(1, repository.fetchSettingsCallCount)
        assertEquals(1, repository.result!!.settingsMapCallCount)

        assertEquals(1, handleRequest.callCount)
        assertEquals(1, mapper.mapCallCount)

        assertEquals(1, mapper.transformBirthdayList.calledList.size)
        assertEquals(birthdayList, mapper.transformBirthdayList.calledList[0])
    }


    private class TestTransformBirthdayListFactory : TransformBirthdayListFactory {

        lateinit var transformBirthdayList: TestTransformBirthdayList
        var mapCallCount = 0

        override fun map(sort: SortMode, reverse: Boolean, group: Boolean): TransformBirthdayList {
            ++mapCallCount
            return transformBirthdayList
        }
    }

    private class TestTransformBirthdayList : TransformBirthdayList {

        var calledList = mutableListOf<BirthdayListDomain>()
        lateinit var result: BirthdayListDomain

        override fun transform(source: BirthdayListDomain): BirthdayListDomain {
            calledList.add(source)
            return result
        }
    }

    private class TestSettingsRepository : Repository.Read<SettingsDomain> {

        var fetchSettingsCallCount = 0
        var result: TestSettings? = null

        override suspend fun read(): SettingsDomain {
            ++fetchSettingsCallCount
            return result ?: throw EmptyCacheException()
        }
    }

    private class TestSettings(
        private val base: SettingsDomain = SettingsDomain.Default
    ) : SettingsDomain by base {
        var settingsMapCallCount = 0

        override fun <T> map(mapper: SettingsDomain.Mapper<T>): T {
            ++settingsMapCallCount
            return base.map(mapper)
        }
    }

    private class TestHandleSettingsDataRequest : TestHandleRequest<SettingsDomain>(),
        HandleSettingsDataRequest
}