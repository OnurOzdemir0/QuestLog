package com.example.questlog.review.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class ScreenshotAdapter : ListAdapter<String, ScreenshotAdapter.ScreenshotViewHolder>(DiffUtilCallback()) {

    class ScreenshotViewHolder(private val imageView: ImageView) : RecyclerView.ViewHolder(imageView) {
        fun bind(url: String) {
            val cornerRadius = 100
            Glide.with(imageView.context)
                .load(url)
                .override(800, 600)
                .transform(CenterCrop(), RoundedCorners(cornerRadius))
                .into(imageView)
        }

        companion object {
            fun from(parent: ViewGroup): ScreenshotViewHolder {
                val imageView = ImageView(parent.context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
                return ScreenshotViewHolder(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotViewHolder {
        return ScreenshotViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ScreenshotViewHolder, position: Int) {
        val url = getItem(position)
        holder.bind(url)
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
