package ru.daniilglazkov.birthdays.ui.birthdays

import androidx.fragment.app.FragmentManager
import ru.daniilglazkov.birthdays.ui.birthdays.birthdayinfo.BirthdaySheetFragment
import ru.daniilglazkov.birthdays.ui.core.Same
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 10.06.2022, 01:05
 */
interface BirthdayUi : Same<BirthdayUi> {
    fun <T> map(mapper: Mapper<T>): T

    fun apply(
        nameView: AbstractView.Text,
        turnsView: AbstractView.Text = AbstractView.Text.Empty,
        dateView: AbstractView.Text = AbstractView.Text.Empty,
        untilView: AbstractView.Text = AbstractView.Text.Empty
    )

    abstract class Abstract(
        private val id: Int,
        private val name: String,
        private val turns: String = "",
        private val date: String = "",
        private val until: String = "",
    ) : BirthdayUi {
        protected abstract val compareMapper: CheckMapper

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(id, name, turns, date, until)

        override fun sameContent(data: BirthdayUi): Boolean = map(CheckMapper.CompareObject(data))
        override fun same(data: BirthdayUi): Boolean = data.map(compareMapper)

        override fun apply(
            nameView: AbstractView.Text,
            turnsView: AbstractView.Text,
            dateView: AbstractView.Text,
            untilView: AbstractView.Text
        ) = nameView.map(name)
    }
    abstract class AbstractCompareName(name: String) : Abstract(id = -1, name) {
        override val compareMapper = CheckMapper.CompareName(name)
    }

    abstract class AbstractCompareId(
        id: Int, name: String, turns: String = "", date: String = "", until: String = ""
    ) : Abstract(id, name, turns, date, until) {
        override val compareMapper = CheckMapper.CompareId(id)
    }

    class Base(id: Int, name: String,
        private val turns: String,
        private val date: String,
        private val until: String,
    ) : AbstractCompareId(id, name, turns, date, until) {
        override fun apply(
            nameView: AbstractView.Text,
            turnsView: AbstractView.Text,
            dateView: AbstractView.Text,
            untilView: AbstractView.Text
        ) {
            super.apply(nameView, turnsView, dateView, untilView)
            turnsView.map(turns)
            dateView.map(date)
            untilView.map(until)
        }
    }

    class Today(id: Int, name: String,
        private val turns: String,
        private val date: String,
        private val until: String,
    ) : AbstractCompareId(id, name, turns, date, until) {
        override fun apply(
            nameView: AbstractView.Text,
            turnsView: AbstractView.Text,
            dateView: AbstractView.Text,
            untilView: AbstractView.Text
        ) {
            super.apply(nameView, turnsView, dateView, untilView)
            turnsView.map(turns)
            dateView.map(date)
            untilView.map(until)
        }
    }
    class Header(name: String) : AbstractCompareName(name)
    class Message(name: String) : AbstractCompareName(name)

    interface Mapper<T> {
        fun map(id: Int, name: String, turns: String, date: String, until: String): T

        class DisplaySheet(
            private val fragmentManager: FragmentManager,
            private val onClosed: () -> Unit = { }
        ) : Mapper<Unit> {
            override fun map(id: Int, name: String, turns: String, date: String, until: String) {
                BirthdaySheetFragment(id).also {
                    it.onClosed(onClosed)
                    it.show(fragmentManager, "birthdaySheetFragment")
                }
            }
        }
    }
    interface CheckMapper : Mapper<Boolean> {

        class CompareName(private val name: String) : CheckMapper {
            override fun map(id: Int, name: String, turns: String, date: String, until: String) =
                this.name == name
        }
        class CompareId(private val id: Int) : CheckMapper {
            override fun map(id: Int, name: String, turns: String, date: String, until: String) =
                this.id == id
        }
        class CompareObject(private val birthdayUi: BirthdayUi) : CheckMapper {
            override fun map(id: Int, name: String, turns: String, date: String, until: String) =
                birthdayUi.map(Content(id, name, turns, date, until))

            private class Content(
                private val id: Int,
                private val name: String,
                private val turns: String,
                private val date: String,
                private val until: String,
            ) : Mapper<Boolean> {
                override fun map(id: Int, name: String, turns: String, date: String, until: String) =
                    (this.id == id).and(this.name == name)
                        .and(this.turns == turns)
                        .and(this.date == date)
                        .and(this.until == until)
            }
        }
    }
}