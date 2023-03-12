package ru.daniilglazkov.birthdays.ui.core.view

import android.content.res.ColorStateList

interface ProvideAttributeColor {

    fun color(id: Int): Int

    fun colorStateList(id: Int): ColorStateList

}