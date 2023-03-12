package ru.daniilglazkov.birthdays.ui.core.view.chip

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.chip.ChipGroup
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.ChipUi
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView
import ru.daniilglazkov.birthdays.ui.core.view.click.OnClickCallback

/**
 * @author Danil Glazkov on 28.07.2022, 02:46
 */
class CustomChipGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ChipGroup(
    context,
    attrs
) , AbstractView.List<ChipUi>,
    SetOnChipClickListener<ChipUi>
{
    private var onClickCallback: OnClickCallback<ChipUi> = OnClickCallback.Unit()

    override fun setOnChipClickListener(callback: OnClickCallback<ChipUi>) {
        onClickCallback = callback
    }

    override fun map(source: List<ChipUi>) {
        removeAllViews()

        if (source.isEmpty()) {
            val chip = CustomChip(context)
            chip.visibility = View.INVISIBLE

            addView(chip)
            return
        }
        source.forEach { chipUi: ChipUi ->
            addView(CustomChip(context).also { newChip: CustomChip ->
                newChip.setOnClickListener {
                    onClickCallback.onClick(chipUi)
                }
                chipUi.apply(newChip)
            })
        }
    }
}