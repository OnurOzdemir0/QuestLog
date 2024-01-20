package com.example.questlog.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.questlog.databinding.ItemReviewsBinding
import com.example.questlog.review.ReviewItem
import com.example.questlog.MarginDecorator

class ReviewCallBacks (val recommendClickListener: ( item : ReviewItem?) -> Unit,
                        val reviewDescChanged  : (item: ReviewItem?, content : String)-> Unit) {

    fun onRecommendClick( item: ReviewItem?)  = recommendClickListener(item)
    fun onReviewDescChange( item : ReviewItem?, content : String) = reviewDescChanged(item,content)

}
class ReviewAdapter(private val callBacks: ReviewCallBacks) : ListAdapter<ReviewItem, ReviewAdapter.ItemViewHolder>(DiffCallBack) {

    class ItemViewHolder(private val binding: ItemReviewsBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var screenshotAdapter: ScreenshotAdapter

        init {
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(binding.carouselItemContainer)
        }

        fun bind(item: ReviewItem, callBacks: ReviewCallBacks) {
            binding.review = item

            if (binding.carouselItemContainer.itemDecorationCount == 0) {
                // Add the MarginDecorator only if it hasn't been added before
                val marginDecorator = MarginDecorator(
                    bottomMargin = 0,
                    topMargin = 0,
                    leftMargin = 8, // For example, 16 pixels of margin to the left of each item
                    rightMargin = 8 // And 16 pixels to the right of each item
                )
                binding.carouselItemContainer.addItemDecoration(marginDecorator)
            }

            screenshotAdapter = ScreenshotAdapter()
            binding.carouselItemContainer.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = screenshotAdapter
            }
            screenshotAdapter.submitList(item.game.screenshotUrl)

            binding.reviewRecommendButton.setOnClickListener {
                callBacks.onRecommendClick(item)
            }
            binding.reviewSendCommentButton.setOnClickListener {
                callBacks.onReviewDescChange(item, binding.reviewDescription.text.toString())
                binding.reviewUserReviewDisplay.text = binding.reviewDescription.text.toString()
                binding.reviewDescription.clearComposingText()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemReviewsBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<ReviewItem>() {
        override fun areItemsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
            return oldItem.game.gameID == newItem.game.gameID
        }

        override fun areContentsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, callBacks)
    }
}