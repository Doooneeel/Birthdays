package ru.daniilglazkov.birthdays.domain.birthday

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException

/**
 * @author Danil Glazkov on 12.02.2023, 12:14
 */
abstract class BaseBirthdayTest {

    protected open class TestBirthdayListRepository : BirthdayListRepository {
        var data: MutableList<BirthdayDomain> = mutableListOf()
        var searchResult: BirthdayDomain? = null
        val findCalledList = mutableListOf<Int>()
        val deleteCalledList = mutableListOf<Int>()
        val addCalledList = mutableListOf<BirthdayDomain>()
        var birthdaysCount = 0
        var firstLaunchCallCount = 0

        var birthdayThrowsException = false
        var firstLaunchResult = false

        override suspend fun find(id: Int): BirthdayDomain {
            findCalledList.add(id)
            return searchResult ?: throw NotFoundException()
        }

        override suspend fun birthdays(): BirthdayListDomain {
            ++birthdaysCount

            return if (birthdayThrowsException)
                throw EmptyCacheException()
            else
                BirthdayListDomain.Base(data)
        }

        override suspend fun delete(id: Int) {
            deleteCalledList.add(id)
            data.removeIf { it.compareId(id) }
        }

        override fun firstLaunch(): Boolean {
            ++firstLaunchCallCount
            return firstLaunchResult
        }

        override suspend fun add(data: BirthdayDomain) {
            addCalledList.add(data)
            this.data.add(data)
        }
    }
}