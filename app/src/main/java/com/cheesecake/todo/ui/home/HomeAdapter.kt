package com.cheesecake.todo.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheesecake.todo.data.models.Tag
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.databinding.ItemHomeHeaderBinding
import com.cheesecake.todo.databinding.ItemTagBinding
import com.hookedonplay.decoviewlib.charts.SeriesItem

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_TAG = 2

class HomeAdapter(
    private val todoItemListener: (item: TodoItem,isPersonal:Boolean) -> Unit,
    private val viewAllListener: (item: String) -> Unit,
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
                holder.bind(
                    item.tag, viewAllListener
                )
            }
        }
    }

    inner class HeaderViewHolder(private val binding: ItemHomeHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(header: DataItem.Header) {
            val personalProgressTodoItem = header.personalTodoItems.count { it.status.value == 1 }.toFloat()
            val personalDoneTodoItem = header.personalTodoItems.count { it.status.value == 2 }.toFloat()
            val teamDoneTodoItem = header.teamTodoItems.count { it.status.value == 2 }.toFloat()
            setPersonalProgress(header, personalDoneTodoItem)
            setTeamProgress(header, teamDoneTodoItem)
            setupPieChart(
                header.personalTodoItems.size.toFloat(),
                personalDoneTodoItem,
                personalProgressTodoItem
            )
        }

        private fun setPersonalProgress(header: DataItem.Header, personalDoneTodoItem: Float) {
            binding.apply {
                personTodoProgressBar.max = header.personalTodoItems.size.toFloat()
                personTodoProgressBar.progress = personalDoneTodoItem
                textViewFixedPersonalProgress.text =
                    ((personalDoneTodoItem * 100) / header.personalTodoItems.size).toString()
            }
        }

        private fun setTeamProgress(header: DataItem.Header, teamDoneTodoItem: Float) {
            binding.apply {
                teamTodoProgressBar.max = header.teamTodoItems.size.toFloat()
                teamTodoProgressBar.progress = teamDoneTodoItem
                textViewFixedTeamProgress.text =
                    ((teamDoneTodoItem * 100) / header.teamTodoItems.size).toString()
            }
        }

        private fun setupPieChart(max: Float, personalDoneTodoItem: Float,
                                  personalProgressTodoItem: Float) {
            val todo = SeriesItem.Builder(Color.argb(92, 225, 225, 225))
                .setRange(0f, max, max)
                .setInitialVisibility(true)
                .setLineWidth(12f)
                .build()

            val progress = SeriesItem.Builder(Color.argb(153, 225, 225, 225))
                .setRange(0f, max, personalProgressTodoItem)
                .setLineWidth(12f)
                .build()

            val done = SeriesItem.Builder(Color.argb(255, 225, 225, 225))
                .setRange(0f, max, personalDoneTodoItem)
                .setLineWidth(12f)
                .build()

            binding.textViewDonePercentage.text =
                ((personalDoneTodoItem * 100) / max).toString()


            binding.pieChart.addSeries(todo)
            binding.pieChart.addSeries(progress)
            binding.pieChart.addSeries(done)
        }

    }

    inner class TagViewHolder(private val binding: ItemTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            tag: Tag,
            listener: (item: String) -> Unit
        ) {
            binding.apply {
                textViewRecently.text = tag.title
                textViewAll.setOnClickListener { listener(tag.title) }
            }
            val adapter = TodoItemAdapter(todoItemListener)
            adapter.submitList(tag.todos)
            binding.recyclerViewRecently.adapter = adapter
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

sealed class DataItem() {
    data class TagItem(val tag: Tag) : DataItem() {
        override val id = tag.id
        override val rank: Int
            get() = tag.rank
    }

    data class Header(val personalTodoItems: List<TodoItem>, val teamTodoItems: List<TodoItem>) :
        DataItem() {
        override val id: Int
            get() = Int.MAX_VALUE
        override val rank: Int
            get() = 0
    }

    abstract val id: Int
    abstract val rank: Int
}