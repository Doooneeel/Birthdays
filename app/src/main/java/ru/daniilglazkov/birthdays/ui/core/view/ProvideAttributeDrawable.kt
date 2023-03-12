package ru.daniilglazkov.birthdays.ui.core.view

import android.graphics.drawable.Drawable

interface ProvideAttributeDrawable {
    fun drawable(id: Int): Drawable?
}