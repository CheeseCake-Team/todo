package com.cheesecake.todo.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.cheesecake.todo.R
import com.cheesecake.todo.databinding.ItemHomeHeaderBinding
import com.cheesecake.todo.databinding.ItemHomeRecyclerBinding
import com.cheesecake.todo.databinding.ItemTodoCardsBinding
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.ui.viewall.VIewAllTodoAdapter
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_TODO_CARDS = 1
private const val ITEM_VIEW_TYPE_TODO_recycler = 2

class HomeAdapter(
    private var homeList: List<*>,
//    private val listener: () -> Unit
private var context: Context

) : RecyclerView.Adapter<HomeAdapter.ItemViewHolderBase>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolderBase {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder(
                ItemHomeHeaderBinding.inflate(
                    layoutInflater, parent, false
                )
            )
            ITEM_VIEW_TYPE_TODO_CARDS -> CardsViewHolder(
                ItemTodoCardsBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            else -> TodoRecyclerViewHolder(
                ItemHomeRecyclerBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }
    }


    override fun onBindViewHolder(holder: ItemViewHolderBase, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val item = homeList[0] as TodoItem
                holder.bind(item)
            }
            is CardsViewHolder -> {
                val item = homeList[1] as TodoItem
                holder.bind(item)
            }
            is TodoRecyclerViewHolder -> {
                val item = homeList[position] as List<TodoItem>
                holder.bind(item)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ITEM_VIEW_TYPE_HEADER
            1 -> ITEM_VIEW_TYPE_TODO_CARDS
            else -> ITEM_VIEW_TYPE_TODO_recycler
        }
    }

    override fun getItemCount() = homeList.size

    abstract class ItemViewHolderBase(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    inner class HeaderViewHolder(private val binding: ItemHomeHeaderBinding) :
        ItemViewHolderBase(binding) {
        val entries = listOf(
            BarEntry(1f, 4f),
            BarEntry(2f, 2f),
            BarEntry(3f, 6f),

        )
        val dataSet = BarDataSet(entries, "Todos").apply {
            barBorderWidth = 0.5f
        }
        val data = BarData(dataSet)
        fun bind(todoItem: TodoItem) {
            dataSet.color = ContextCompat.getColor(context, R.color.md_theme_light_primary) // get the color from the color resource file

            binding.apply{
                barChart.data = data
                barChart.xAxis.labelCount = 0
                barChart.axisLeft.axisMinimum = 0f
                barChart.axisRight.isEnabled = false

                barChart.invalidate()

            }
        }
    }
    inner class CardsViewHolder(private val binding: ItemTodoCardsBinding) :
        ItemViewHolderBase(binding) {
        fun bind(todoItem: TodoItem) {
        }
    }
    inner class TodoRecyclerViewHolder(private val binding: ItemHomeRecyclerBinding) :
        ItemViewHolderBase(binding) {
        fun bind(todoItem: List<TodoItem>) {
            if (todoItem[0].assignee == null){
                binding.textRecently.text = "Recently Personal"
            } else {
                binding.textRecently.text = "Recently Team"
            }
            binding.recyclerView.adapter = VIewAllTodoAdapter(todoItem)
            //binding.textViewAll.setOnClickListener { listener }
        }

    }



}