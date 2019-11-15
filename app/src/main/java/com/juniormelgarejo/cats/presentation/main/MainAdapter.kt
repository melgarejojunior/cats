package com.juniormelgarejo.cats.presentation.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainAdapter : RecyclerView.Adapter<ImageViewHolder>() {

    private var items: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.inflate(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    internal fun setItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }
}