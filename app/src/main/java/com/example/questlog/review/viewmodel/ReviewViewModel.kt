package com.example.questlog.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.questlog.database.PlaylistDatabase
import com.example.questlog.database.ReviewDataBase
import com.example.questlog.playlist.PlayListItem
import com.example.questlog.review.ReviewItem
import kotlinx.coroutines.runBlocking


class ReviewViewModel : ViewModel() {

    private val reviewDatabase_ = ReviewDataBase()
    var userID: String? = null


    fun addNewReview(review: ReviewItem) {
        runBlocking {

            userID?.let {
                   if(reviewDatabase_.addReviewItem(it,review.listing.game.gameID,review.isRecommended,review.description)){
                       println("review addedd")
                   }
                }


        }

    }
    fun addNewReviewSafe(review: ReviewItem) {
        var check :Boolean = false
        runBlocking {
            userID?.let {
                if(!reviewDatabase_.checkIfReviewExists(it,review.listing.game.gameID)){
                    if(reviewDatabase_.addReviewItem(it,review.listing.game.gameID,review.isRecommended,review.description)){
                        println("review addedd")
                    }
                }
            }


        }

    }

    fun changeRecommendState(item : ReviewItem?){
        if(item ==null) return
        runBlocking {
            userID?.let {
                if(reviewDatabase_.addReviewItem(it,item.listing.game.gameID,!item.isRecommended,item.description)){
                    println("review addedd")
                }
            }
        }
    }
    fun changeDesc(item : ReviewItem?, content : String){
        if(item ==null) return
        runBlocking {
            userID?.let {
                if(reviewDatabase_.addReviewItem(it,item.listing.game.gameID,item.isRecommended,content)){
                    println("review addedd")
                }
            }
        }

    }

    fun getReviewList(): List<ReviewItem> {
        val list :List<ReviewItem>?
        runBlocking {
            list  = userID?.let { reviewDatabase_.getReviewItems(it) }
        }
        if(list!=null){
            return list
        }else{
            return emptyList()
        }
    }
}
