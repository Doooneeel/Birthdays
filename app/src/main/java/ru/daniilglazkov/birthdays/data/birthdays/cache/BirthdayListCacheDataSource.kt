package ru.daniilglazkov.birthdays.data.birthdays.cache

import ru.daniilglazkov.birthdays.core.Add
import ru.daniilglazkov.birthdays.core.Delete
import ru.daniilglazkov.birthdays.core.Read
import ru.daniilglazkov.birthdays.data.birthdays.BirthdayData
import ru.daniilglazkov.birthdays.data.birthdays.BirthdayEntity
import ru.daniilglazkov.birthdays.data.main.ProvideBirthdaysAccess
import ru.daniilglazkov.birthdays.domain.core.Find
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException

/**
 * @author Danil Glazkov on 08.09.2022, 03:52
 */
interface BirthdayListCacheDataSource : Read<List<BirthdayData>>, Add<BirthdayData>, Delete,
    Find<BirthdayData> {

    class Base(
        provideAccess: ProvideBirthdaysAccess,
        private val dataToDatabaseModel: BirthdayData.Mapper<BirthdayEntity>
    ) : BirthdayListCacheDataSource {
        private val access = provideAccess.birthdaysAccess()

        override fun read(): List<BirthdayData> {
            val birthdays = access.allBirthdays()
            return birthdays.map { it.mapToData() }
        }

        override fun add(data: BirthdayData) = access.insert(data.map(dataToDatabaseModel))

        override fun delete(id: Int) = access.delete(id)

        override fun find(id: Int): BirthdayData {
            return access.find(id)?.mapToData() ?: throw NotFoundException("id: $id")
        }
    }
}