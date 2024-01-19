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


