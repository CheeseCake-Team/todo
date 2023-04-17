package com.cheesecake.todo.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheesecake.todo.data.models.Tag
import com.cheesecake.todo.databinding.ItemHomeHeaderBinding
import com.cheesecake.todo.databinding.ItemTagBinding
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.charts.SeriesLabel

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_TAG = 2

class HomeAdapter(
    private val listener: TodoItemClickListener,
) : ListAdapter<DataItem, RecyclerView.ViewHolder>(DataItemDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.TagItem -> ITEM_VIEW_TYPE_TAG
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder(
                ItemHomeHeaderBinding.inflate(
                    layoutInflater, parent, false
                )
            )
            ITEM_VIEW_TYPE_TAG -> TagViewHolder(
                ItemTagBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            else -> throw java.lang.ClassCastException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val item = getItem(position) as DataItem.Header
                holder.bind(item)
            }
            is TagViewHolder -> {
                val item = getItem(position) as DataItem.TagItem
                holder.bind(item.tag)
            }
        }
    }

    inner class HeaderViewHolder(private val binding: ItemHomeHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(header: DataItem.Header) {
            setupPieChart()
            binding.personTodoProgressBar.max = (header.doneNumber + header.progressNumber
                    + header.todoNumber).toFloat()
            binding.personTodoProgressBar.progress = header.doneNumber.toFloat()
        }

        private fun setupPieChart() {
            val todo = SeriesItem.Builder(Color.argb(92, 225, 225, 225))
                .setRange(0f, 100f, 100f)
                .setInitialVisibility(true)
                .setLineWidth(12f)
                .build()

            val progress = SeriesItem.Builder(Color.argb(153, 225, 225, 225))
                .setRange(0f, 100f, 80f)
                .setLineWidth(12f)
                .build()

            val done = SeriesItem.Builder(Color.argb(255, 225, 225, 225))
                .setRange(0f, 100f, 60f)
                .setLineWidth(12f)
                .build()

            binding.pieChart.addSeries(todo)
            binding.pieChart.addSeries(progress)
            binding.pieChart.addSeries(done)
        }

    }

    inner class TagViewHolder(private val binding: ItemTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: Tag) {
            binding.textRecently.text = tag.title
            val adapter = TodoItemAdapter(listener)
            adapter.submitList(tag.todos)
            binding.recyclerView.adapter = adapter
        }

    }

}

class DataItemDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}


sealed class DataItem {
    data class TagItem(val tag: Tag) : DataItem() {
        override val id = tag.id
    }

    data class Header(val todoNumber: Int, val progressNumber: Int, val doneNumber: Int) :
        DataItem() {
        override val id: Int
            get() = Int.MAX_VALUE
    }

    abstract val id: Int
}