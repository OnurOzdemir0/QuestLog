package com.example.questlog.review.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questlog.R
import com.example.questlog.databinding.ItemPlaylistBinding
import com.example.questlog.databinding.ItemReviewsBinding
import com.example.questlog.playlist.PlayListItem
import com.example.questlog.playlist.adapter.PlaylistAdapter
import com.example.questlog.review.ReviewItem
import com.bumptech.glide.Glide
class ReviewCallBacks (val recommendClickListener: ( item : ReviewItem?) -> Unit,
                        val reviewDescChanged  : (item: ReviewItem?, content : String)-> Unit) {

    fun onRecommendClick( item: ReviewItem?)  = recommendClickListener(item)
    fun onReviewDescChange( item : ReviewItem?, content : String) = reviewDescChanged(item,content)

}
class ReviewAdapter ( val callBacks: ReviewCallBacks): ListAdapter<ReviewItem,ReviewAdapter.ItemViewHolder>(DiffCallBack) {
    class ItemViewHolder (val binding : ItemReviewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind( item : ReviewItem, callBacks: ReviewCallBacks){
            binding.review  = item

            val screenshotUrl = item.listing.game.screenshotUrl?.firstOrNull()
            if (!screenshotUrl.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(screenshotUrl)
                    .placeholder(R.drawable.i_1)
                    .error(R.drawable.i_1)
                    .into(binding.reviewGameImage)
            } else {
                binding.reviewGameImage.setImageResource(R.drawable.i_1)
            }


            binding.reviewRecommendButton.setOnClickListener{
                callBacks.onRecommendClick(binding.review)
            }
            binding.reviewSendCommentButton.setOnClickListener{
                println(binding.reviewDescription.toString())
                callBacks.onReviewDescChange(binding.review, binding.reviewDescription.text.toString())
                binding.reviewUserReviewDisplay.text = binding.reviewDescription.text.toString()
                binding.reviewDescription.clearComposingText()
            }
            /*
            olmadı bu :<
            binding.reviewDescription.removeTextChangedListener(binding.reviewDescription.tag as? TextWatcher)
            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    callBacks.onReviewDescChange(binding.review,s.toString())
                }
            }
            binding.reviewDescription.addTextChangedListener(textWatcher)
            binding.reviewDescription.tag = textWatcher
            */
        }


        companion object {
            fun from (parent: ViewGroup) : ReviewAdapter.ItemViewHolder {
                val layoutInflater  = LayoutInflater.from(parent.context)
                val binding = ItemReviewsBinding.inflate(LayoutInflater.from(parent.context))
                return ReviewAdapter.ItemViewHolder(binding)
            }
        }
    }
    companion object  DiffCallBack : DiffUtil.ItemCallback<ReviewItem>(){

        override fun areContentsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
            return oldItem.listing.game.gameID == newItem.listing.game.gameID
        }

        override fun areItemsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ItemViewHolder {
        return ReviewAdapter.ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,callBacks)
    }

}