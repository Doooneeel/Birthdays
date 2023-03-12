package ru.daniilglazkov.birthdays.ui.core.view.button

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode
import ru.daniilglazkov.birthdays.ui.core.view.sortmode.SortModeAbstractView

/**
 * @author Danil Glazkov on 16.08.2022, 03:09
 */
abstract class AbstractSortModeRadioButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    private val sortMode: SortMode
) : AppCompatRadioButton(context, attrs),
    SortModeAbstractView
{
    override fun setOnSortModeRadioButtonClick(selectedMode: (SortMode) -> Unit) =
        setOnClickListener { selectedMode.invoke(sortMode) }

    override fun map(source: SortMode) {
        isChecked = sortMode == source
    }
}

class SortByDateRadioButton(context: Context, attrs: AttributeSet?) :
    AbstractSortModeRadioButton(context, attrs, SortMode.DATE)

class SortByNameRadioButton(context: Context, attrs: AttributeSet?) :
    AbstractSortModeRadioButton(context, attrs, SortMode.NAME)

class SortByAgeRadioButton(context: Context, attrs: AttributeSet?) :
    AbstractSortModeRadioButton(context, attrs, SortMode.AGE)

class SortByYearRadioButton(context: Context, attrs: AttributeSet?) :
    AbstractSortModeRadioButton(context, attrs, SortMode.YEAR)

class SortByZodiacRadioButton(context: Context, attrs: AttributeSet?) :
    AbstractSortModeRadioButton(context, attrs, SortMode.ZODIAC)

class SortByMonthRadioButton(context: Context, attrs: AttributeSet?) :
    AbstractSortModeRadioButton(context, attrs, SortMode.MONTH)