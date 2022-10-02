package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.switchmaterial.SwitchMaterial

/**
 * @author Danil Glazkov on 16.08.2022, 02:22
 */
class CustomSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwitchMaterial(
    context,
    attrs
) , AbstractView.Check {
    override fun map(source: Boolean) { isChecked = source }
}