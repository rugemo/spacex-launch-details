package com.russellmorris.pastlaunches.ui.view

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.russellmorris.extensions.inflate
import com.russellmorris.extensions.parseUtcDate
import com.russellmorris.pastlaunches.R
import com.russellmorris.pastlaunches.ui.model.Launch
import kotlinx.android.synthetic.main.item_launch.view.*

class LaunchesAdapter constructor(private val itemClick: (Launch) -> Unit) :
    ListAdapter<Launch, LaunchesAdapter.ViewHolder>(LaunchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.item_launch)) {

        fun bind(item: Launch) {
            itemView.missionName.text = item.missionName
            itemView.date.text = item.launchDateUtc?.parseUtcDate()
            itemView.setOnClickListener { itemClick.invoke(item) }
        }
    }
}

private class LaunchDiffCallback : DiffUtil.ItemCallback<Launch>() {
    override fun areItemsTheSame(oldItem: Launch, newItem: Launch): Boolean =
        oldItem.missionName == newItem.missionName

    override fun areContentsTheSame(oldItem: Launch, newItem: Launch): Boolean =
        oldItem == newItem
}