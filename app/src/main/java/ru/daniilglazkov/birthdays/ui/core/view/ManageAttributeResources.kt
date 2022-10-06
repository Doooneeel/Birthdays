package ru.daniilglazkov.birthdays.ui.core.view

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable

/**
 * @author Danil Glazkov on 03.10.2022, 16:14
 */
interface ManageAttributeResources : ProvideAttributeString, ProvideAttributeColor,
    ProvideAttributeDrawable, ProvideAttributeResourceId, Recycle {

    class Base(private val typedArray: TypedArray) : ManageAttributeResources {
        override fun string(id: Int): String = typedArray.getString(id) ?: ""

        override fun colorState(id: Int): ColorStateList {
            return typedArray.getColorStateList(id) ?: ColorStateList.valueOf(Color.TRANSPARENT)
        }
        override fun drawable(id: Int): Drawable? = typedArray.getDrawable(id)

        override fun id(resourceId: Int): Int = typedArray.getResourceId(resourceId, 0)

        override fun color(id: Int): Int = typedArray.getColor(id, Color.TRANSPARENT)

        override fun recycle() = typedArray.recycle()
    }
}

interface ProvideAttributeString {
    fun string(id: Int): String
}

interface ProvideAttributeColor {
    fun color(id: Int): Int
    fun colorState(id: Int): ColorStateList
}
interface ProvideAttributeDrawable {
    fun drawable(id: Int): Drawable?
}

interface ProvideAttributeResourceId {
    fun id(resourceId: Int): Int
}

interface Recycle {
    fun recycle()
}