package com.juniormelgarejo.cats.presentation.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.juniormelgarejo.cats.R
import com.juniormelgarejo.cats.databinding.ItemImageBinding

class ImageViewHolder private constructor(
    private val binding: ItemImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val context: Context by lazy { binding.root.context }
    private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
    private val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_photo_24px)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .fitCenter()

    fun bind(url: String) {
        Glide.with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(requestOptions)
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