package ru.daniilglazkov.birthdays.ui.birthdaylist

/**
 * @author Danil Glazkov on 26.10.2022, 07:52
 */
interface BirthdayItemUiCompareMapper : BirthdayItemUi.Mapper<Boolean> {

    class Name(private val name: String) : BirthdayItemUiCompareMapper {
        override fun map(id: Int, name: String, turnsYearsOld: String, daysToBirthday: String) =
            this.name == name
    }

    class Content(
        private val id: Int,
        private val name: String,
        private val turnsYearsOld: String,
        private val daysToBirthday: String,
    ) : BirthdayItemUiCompareMapper {
        override fun map(id: Int, name: String, turnsYearsOld: String, daysToBirthday: String) =
            this.id == id && this.name == name && this.turnsYearsOld == turnsYearsOld &&
                    this.daysToBirthday == daysToBirthday
    }

    class Object(private val birthdayUi: BirthdayItemUi) : BirthdayItemUiCompareMapper {
        override fun map(id: Int, name: String, turnsYearsOld: String, daysToBirthday: String) =
            birthdayUi.map(Content(id, name, turnsYearsOld, daysToBirthday))
    }
}