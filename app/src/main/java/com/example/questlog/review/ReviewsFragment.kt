package com.example.questlog.review

import com.example.questlog.review.viewmodel.ReviewViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questlog.R
import com.example.questlog.databinding.FragmentReviewsBinding
import com.example.questlog.review.adapter.ReviewAdapter
import com.example.questlog.review.adapter.ReviewCallBacks


class ReviewsFragment : Fragment() {


    private lateinit var reviewViewModel: ReviewViewModel
    private lateinit var binding: FragmentReviewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = DataBindingUtil.inflate(
            inflater,R.layout.fragment_reviews,
            container, false
        )
        val callback  = ReviewCallBacks(
            recommendClickListener = { item ->OnRecommendClick(item) },
            reviewDescChanged =  {item,content -> OnReviewDescChange(item,content)}
        )
        var adapter : ReviewAdapter = ReviewAdapter(callback)
        binding.reviewRecyclerview.adapter = adapter
        binding.reviewRecyclerview.layoutManager = LinearLayoutManager(context)

        reviewViewModel = ViewModelProvider(requireActivity()).get(ReviewViewModel::class.java)
        // Inflate the layout for this fragment

        adapter.submitList(reviewViewModel.getReviewList())
        return binding.root

    }

    fun OnRecommendClick( item:ReviewItem?){

         reviewViewModel.changeRecommendState(item)

    }
    fun OnReviewDescChange(item : ReviewItem?, content : String){
        reviewViewModel.changeDesc(item,content)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun getReview(): List<ReviewItem> {
        return reviewViewModel.getReviewList()
    }
}



/*
class ReviewAdapter(private val reviews: List<ReviewItem>, private val recommendClickListener: (Int, Boolean) -> Unit) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameImage: ImageView = view.findViewById(R.id.review_game_image)
        val gameName: TextView = view.findViewById(R.id.review_game_name)
        val gameDescription: TextView = view.findViewById(R.id.review_game_description)
        val recommendButton: Button = view.findViewById(R.id.review_recommend_button)
        val generalScore: TextView = view.findViewById(R.id.review_general_score)
        val userScore: TextView = view.findViewById(R.id.review_user_score)
        val userName: TextView = view.findViewById(R.id.review_user_name)
        val description: TextView = view.findViewById(R.id.review_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reviews, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]

        holder.gameName.text = review.game.name
        holder.gameDescription.text = review.game.desc
        holder.generalScore.text = review.game.generalRating.toString()
        holder.userScore.text = review.game.userRating.toString()
       // holder.userName.text = "User: ${review.user.userName}"
        holder.description.text = review.description

        val screenshotUrl = review.game.screenshotUrl?.firstOrNull()
        if (!screenshotUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(screenshotUrl)
                .placeholder(R.drawable.i_1)
                .error(R.drawable.i_1)
                .into(holder.gameImage)
        } else {
            holder.gameImage.setImageResource(R.drawable.i_1)
        }

        holder.recommendButton.setOnClickListener {
            review.isRecommended = !review.isRecommended
            recommendClickListener(position, review.isRecommended)
            notifyDataSetChanged()
        }

        holder.description.removeTextChangedListener(holder.description.tag as? TextWatcher)
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                review.description = s.toString()
            }
        }

        holder.description.addTextChangedListener(textWatcher)
        holder.description.tag = textWatcher
    }

    override fun getItemCount(): Int = reviews.size
}
*/
