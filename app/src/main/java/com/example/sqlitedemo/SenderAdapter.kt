package com.example.sqlitedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedemo.databinding.CustomNotesBinding


class SenderAdapter(private val context: Context, private var items: List<User>) :
    RecyclerView.Adapter<SenderAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CustomNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.idRec.text = item.id.toString()
            binding.nameRec.text=item.name
            binding.ageRec.text=item.age.toString()

        }
    }
   fun updateData(newData: List<User>) {
        items = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CustomNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = items.size
}
