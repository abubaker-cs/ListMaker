package org.abubaker.listmaker.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.abubaker.listmaker.R

class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val taskTextView = itemView.findViewById(R.id.textview_task) as TextView
}