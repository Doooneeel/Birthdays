package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.util.AttributeSet
import android.widget.DatePicker
import java.time.LocalDate

/**
 * @author Danil Glazkov on 26.06.2022, 15:23
 */
class CustomDatePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : DatePicker(
    context,
    attrs
) , AbstractView.Date {
    init {
        maxDate = System.currentTimeMillis()
    }
    val date: LocalDate get() = LocalDate.of(year, month + 1, dayOfMonth)

    override fun map(source: LocalDate) = source.let {
        updateDate(it.year, it.monthValue - 1, it.dayOfMonth)
    }
}