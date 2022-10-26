package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler

/**
 * @author Danil Glazkov on 26.10.2022, 07:52
 */
interface BirthdayItemUiCheckMapper : BirthdayItemUi.Mapper<Boolean> {

    class CompareName(private val name: String) : BirthdayItemUiCheckMapper {
        override fun map(id: Int, name: String, turnsYearsOld: String, daysToBirthday: String) =
            this.name == name
    }

    class CompareObject(private val birthdayUi: BirthdayItemUi) : BirthdayItemUiCheckMapper {
        override fun map(id: Int, name: String, turnsYearsOld: String, daysToBirthday: String) =
            birthdayUi.map(Content(id, name, turnsYearsOld, daysToBirthday))

        private class Content(
            private val id: Int,
            private val name: String,
            private val turnsYearsOld: String,
            private val daysToBirthday: String,
        ) : BirthdayItemUiCheckMapper {
            override fun map(id: Int, name: String, turnsYearsOld: String, daysToBirthday: String) =
                this.id == id && this.name == name && this.turnsYearsOld == turnsYearsOld &&
                        this.daysToBirthday == daysToBirthday
        }
    }
}