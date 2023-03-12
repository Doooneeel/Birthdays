package ru.daniilglazkov.birthdays.ui.core.view.image

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import ru.daniilglazkov.birthdays.ui.core.view.AbstractResView

/**
 * @author Danil Glazkov on 02.11.2022, 10:39
 */
class CustomResImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(
    context,
    attrs
) , AbstractResView.Image {
    override fun map(source: Int) = setImageDrawable(
        AppCompatResources.getDrawable(context, source)
    )
}