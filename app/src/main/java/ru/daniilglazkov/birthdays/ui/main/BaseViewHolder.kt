package ru.daniilglazkov.birthdays.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Danil Glazkov on 10.06.2022, 02:18
 */
abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: T)
}