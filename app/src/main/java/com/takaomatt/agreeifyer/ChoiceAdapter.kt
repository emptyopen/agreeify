package com.takaomatt.agreeifyer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.table_row.view.*

class ChoiceAdapter(private val context: Context,
                    private val items: ArrayList<Choice>) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.table_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvDescription?.text = items.get(position).description
        holder.tvTags?.text = items.get(position).tags
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val tvDescription = view.description
    val tvTags = view.tags
}