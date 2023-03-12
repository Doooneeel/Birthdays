package ru.daniilglazkov.birthdays.ui.core.view.picker

import android.widget.DatePicker
import java.time.LocalDate

/**
 * @author Danil Glazkov on 14.11.2022, 01:51
 */
class OnLocalDateChangeListener(
    private val onLocalDateChanged: (LocalDate) -> Unit,
) : DatePicker.OnDateChangedListener {
    override fun onDateChanged(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        onLocalDateChanged.invoke(LocalDate.of(year, monthOfYear + 1, dayOfMonth))
    }
}