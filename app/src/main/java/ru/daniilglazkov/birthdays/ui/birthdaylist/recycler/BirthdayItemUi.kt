package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler

import androidx.fragment.app.FragmentManager
import ru.daniilglazkov.birthdays.ui.birthday.BirthdaySheetFragment
import ru.daniilglazkov.birthdays.ui.core.Same
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 10.06.2022, 01:05
 */
interface BirthdayItemUi : Same<BirthdayItemUi> {
    fun <T> map(mapper: Mapper<T>): T

    fun apply(
        name: AbstractView.Text,
        turnsYearsOld: AbstractView.Text,
        daysToBirthday: AbstractView.Text
    ) = Unit

    fun applyHeader(text: AbstractView.Text) = Unit


    abstract class Abstract(
        private val id: Int,
        private val name: String,
        private val turnsYearsOld: String = "",
        private val daysToBirthday: String = ""
    ) : BirthdayItemUi {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(id, name, turnsYearsOld, daysToBirthday)

        override fun same(data: BirthdayItemUi): Boolean =
            data.map(BirthdayItemUiCheckMapper.CompareName(name))

        override fun sameContent(data: BirthdayItemUi): Boolean  =
            data.map(BirthdayItemUiCheckMapper.CompareObject(data))
    }

    class Header(id: Int, private val name: String) : Abstract(id, name) {
        override fun applyHeader(text: AbstractView.Text) =
            text.map(name)
    }

    class Message(private val text: String) : Abstract(Int.MIN_VALUE, text) {
        override fun applyHeader(text: AbstractView.Text) =
            text.map(this.text)
    }

    class Base(
        id: Int,
        private val name: String,
        private val turnsYearsOld: String,
        private val daysToBirthday: String
    ) : Abstract(id, name, turnsYearsOld, daysToBirthday) {
        override fun apply(
            name: AbstractView.Text,
            turnsYearsOld: AbstractView.Text,
            daysToBirthday: AbstractView.Text
        ) {
            name.map(this.name)
            turnsYearsOld.map(this.turnsYearsOld)
            daysToBirthday.map(this.daysToBirthday)
        }
    }

    class Today(
        id: Int,
        private val name: String,
        private val turnsYearsOld: String
    ) : Abstract(id, name, turnsYearsOld) {
        override fun apply(
            name: AbstractView.Text,
            turnsYearsOld: AbstractView.Text,
            daysToBirthday: AbstractView.Text
        ) {
            name.map(this.name)
            turnsYearsOld.map(this.turnsYearsOld)
        }
    }


    interface Mapper<T> {
        fun map(id: Int, name: String, turnsYearsOld: String, daysToBirthday: String): T

        class DisplaySheet(
            private val fragmentManager: FragmentManager,
            private val onClosed: () -> Unit = { }
        ) : Mapper<Unit> {
            override fun map(id: Int, name: String, turnsYearsOld: String, daysToBirthday: String) {
                BirthdaySheetFragment(id).also {
                    it.onClosed(onClosed)
                    it.show(fragmentManager, "birthdaySheetFragment")
                }
            }
        }
    }
}