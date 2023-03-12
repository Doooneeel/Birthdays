package ru.daniilglazkov.birthdays.ui.birthday

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

class DaysLeftView(
    private val left: AbstractView.Text,
    private val days: AbstractView.Text,
) : AbstractView<DaysLeft> {
    override fun map(source: DaysLeft) = source.apply(left, days)
}