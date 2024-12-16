package com.codepath.bitlife

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codepath.bitlife.data.DisplayWaterIntake

class WaterIntakeAdapter(
    private val context: Context,
    private val entries: MutableList<DisplayWaterIntake>
) : RecyclerView.Adapter<WaterIntakeAdapter.ViewHolder>() {

    // Define the ViewHolder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.itemDate)
        val amountTextView: TextView = itemView.findViewById(R.id.itemAmount)

        fun bind(waterIntake: DisplayWaterIntake) {
            dateTextView.text = waterIntake.date
            amountTextView.text = "${waterIntake.amount} ml"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val waterIntake = entries[position]
        holder.bind(waterIntake)
    }

    override fun getItemCount(): Int = entries.size

    // Method to update the list of entries
    fun updateEntries(newEntries: List<DisplayWaterIntake>) {
        entries.clear()
        entries.addAll(newEntries)
        notifyDataSetChanged()
    }
}
