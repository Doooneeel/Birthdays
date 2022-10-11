package ru.daniilglazkov.birthdays.ui.main

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.daniilglazkov.birthdays.ui.core.Mapper
import ru.daniilglazkov.birthdays.ui.core.Same

/**
 * @author Danil Glazkov on 10.06.2022, 02:39
 */
abstract class BaseAdapter<VH : BaseViewHolder<VT>, VT : Same<VT>> : RecyclerView.Adapter<VH>(),
    Mapper.Unit<List<VT>>
{
    protected lateinit var layoutInflater: LayoutInflater
    protected abstract fun getViewType(data: VT): Int

    private val mutableList: MutableList<VT> = mutableListOf()


    override fun getItemViewType(position: Int): Int = getViewType(mutableList[position])

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        layoutInflater = LayoutInflater.from(recyclerView.context)
        super.onAttachedToRecyclerView(recyclerView)
    }
    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(mutableList[position])

    override fun getItemCount(): Int = mutableList.size

    abstract fun diffUtilCallback(oldList: MutableList<VT>, newList: List<VT>): DiffUtil.Callback

    override fun map(source: List<VT>) {
        val result = DiffUtil.calculateDiff(diffUtilCallback(mutableList, source))
        mutableList.clear()
        mutableList.addAll(source)
        result.dispatchUpdatesTo(this)
    }
}