package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton
import ru.daniilglazkov.birthdays.domain.showmode.sort.SortMode
import ru.daniilglazkov.birthdays.ui.settings.showmode.SortModeAbstractView

/**
 * @author Danil Glazkov on 16.08.2022, 03:09
 */
abstract class AbstractSortModeRadioButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    private val sortMode: SortMode
) : AppCompatRadioButton(
    context,
    attrs
) , SortModeAbstractView {
    override fun setOnSortModeRadioButtonClick(block: (SortMode) -> Unit) = setOnClickListener {
        block.invoke(sortMode)
    }
    override fun map(source: SortMode) {
        isChecked = sortMode == source
    }
}

class SortByDateRadioButton(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractSortModeRadioButton(context, attrs, SortMode.DATE)

class SortByNameRadioButton(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractSortModeRadioButton(context, attrs, SortMode.NAME)

class SortByAgeRadioButton(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractSortModeRadioButton(context, attrs, SortMode.AGE)

class SortByYearRadioButton(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractSortModeRadioButton(context, attrs, SortMode.YEAR)

class SortByZodiacRadioButton(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractSortModeRadioButton(context, attrs, SortMode.ZODIAC)

class SortByMonthRadioButton(
    context: Context,
    attrs: AttributeSet? = null
) : AbstractSortModeRadioButton(context, attrs, SortMode.MONTH)