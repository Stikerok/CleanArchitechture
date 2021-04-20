package com.example.cleanarchitechture.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitechture.R
import com.example.cleanarchitechture.entity.Person

class PersonAdapterFilter internal constructor(
    private var data: List<Person>
) : RecyclerView.Adapter<PersonAdapterFilter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.operation_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = data[position]
        viewHolder.text.text = item.toString()
    }

    override fun getItemCount() = data.size

    fun setData(data: List<Person>){
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.operation_text)


    }


}