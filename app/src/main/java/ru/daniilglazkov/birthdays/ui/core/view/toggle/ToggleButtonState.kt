package ru.daniilglazkov.birthdays.ui.core.view.toggle

import android.widget.Button
import androidx.core.widget.TextViewCompat
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.ui.core.view.ManageAttributeResources

/**
 * @author Danil Glazkov on 07.10.2022, 14:15
 */
interface ToggleButtonState {

    fun apply(view: Button)


    abstract class Abstract(
        manageResources: ManageAttributeResources,
        text: Int,
        backgroundTint: Int,
        color: Int,
        drawable: Int
    ) : ToggleButtonState {
        private val text = manageResources.string(text)
        private val backgroundTint = manageResources.colorStateList(backgroundTint)
        private val color = manageResources.colorStateList(color)
        private val drawableId = manageResources.id(drawable)

        override fun apply(view: Button) {
            view.text = text
            view.backgroundTintList = backgroundTint
            view.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)

            view.setTextColor(color)
            TextViewCompat.setCompoundDrawableTintList(view, color)
        }
    }

    class Disable(manageResources: ManageAttributeResources) : Abstract(
        manageResources,
        R.styleable.ToggleButtonView_disableText,
        R.styleable.ToggleButtonView_disableBackgroundTint,
        R.styleable.ToggleButtonView_disableColor,
        R.styleable.ToggleButtonView_disableDrawable,
    )

    class Enable(manageResources: ManageAttributeResources) : Abstract(
        manageResources,
        R.styleable.ToggleButtonView_enableText,
        R.styleable.ToggleButtonView_enableBackgroundTint,
        R.styleable.ToggleButtonView_enableColor,
        R.styleable.ToggleButtonView_enableDrawable,
    )

}