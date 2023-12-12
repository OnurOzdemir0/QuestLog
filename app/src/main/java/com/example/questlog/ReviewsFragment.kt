package com.example.questlog

import ReviewPopupFragment
import ReviewViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class ReviewsFragment : Fragment() {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView

    private lateinit var reviewViewModel: ReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reviews, container, false)

        recyclerView = view.findViewById(R.id.review_recyclerview)
        searchView = view.findViewById(R.id.review_search)
        reviewViewModel = ViewModelProvider(requireActivity()).get(ReviewViewModel::class.java)
        // Inflate the layout for this fragment
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)


        reviewViewModel.reviews.observe(viewLifecycleOwner) { newList ->
            recyclerView.adapter = ReviewAdapter(newList) { position, isRecommended ->
                // Handle recommendation click
                val review = reviewViewModel.reviews.value?.get(position)
                review?.isRecommended = isRecommended
                // Perform any additional actions based on the recommendation status
            }

            reviewViewModel.reviews.observe(viewLifecycleOwner) {
                recyclerView.adapter = ReviewAdapter(it) { position, isRecommended ->
                    val review = reviewViewModel.reviews.value?.get(position)
                    review?.isRecommended = isRecommended

                    // Show review popup
                    showReviewPopup { reviewText ->
                        // Handle review submission, e.g., save the reviewText to your ViewModel
                        // You may also want to update the UI with the new review data
                    }
                }
            }



        }




    }

    private fun showReviewPopup(onReviewSubmitted: (String) -> Unit) {
        val reviewPopup = ReviewPopupFragment()
        reviewPopup.setReviewListener(object : ReviewPopupFragment.ReviewListener {
            override fun onReviewSubmitted(reviewText: String) {
                onReviewSubmitted.invoke(reviewText)
            }
        })
        reviewPopup.show(parentFragmentManager, "ReviewPopupFragment")
    }

    private fun getReview(): List<Review> {
        return reviewViewModel.getReviewList()
    }
}

data class Review(val game: Game, val description: String, var isRecommended: Boolean = false)

class ReviewAdapter(private val reviews: List<Review>, private val recommendClickListener: (Int, Boolean) -> Unit) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

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

        // Set values from the Review object to corresponding views
        holder.gameName.text = review.game.name
        holder.gameDescription.text = review.game.desc
        holder.generalScore.text = review.game.generalRating.toString()
        holder.userScore.text = review.game.userRating.toString()
       // holder.userName.text = "User: ${review.user.userName}"
        holder.description.text = review.description

        // Load the image dynamically based on the gameID
        val iconName = "i_${review.game.gameID}"
        val resourceId = holder.itemView.context.resources.getIdentifier(iconName, "drawable", holder.itemView.context.packageName)
        if (resourceId != 0) { // Resource exists
            holder.gameImage.setImageResource(resourceId)
        } else {
            holder.gameImage.setImageResource(R.drawable.i_1)
        }

        // Set click listener for the recommend button
        holder.recommendButton.setOnClickListener {
            // Handle recommend button click
            review.isRecommended = !review.isRecommended
            recommendClickListener(position, review.isRecommended)
            notifyDataSetChanged() // Notify adapter to update the UI
        }
    }

    override fun getItemCount(): Int = reviews.size
}