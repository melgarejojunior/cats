package com.juniormelgarejo.cats.presentation.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juniormelgarejo.cats.databinding.ItemImageBinding

class ImageViewHolder private constructor(
    private val binding: ItemImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val context: Context by lazy { binding.root.context }

    fun bind(url: String) {
        Glide.with(context)
            .load(url)
            .into(binding.image)
    }

    companion object {
        fun inflate(parent: ViewGroup): ImageViewHolder {
            return ImageViewHolder(
                ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}