package com.example.questlog.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.questlog.review.ReviewItem



class ReviewViewModel : ViewModel() {

    private val _reviews = MutableLiveData<List<ReviewItem>>()

    val reviews: LiveData<List<ReviewItem>>get() = _reviews
    /*
    init {
        // Populate with mock data
        val mockUser1 = Game(1, "Game 1", "Description 1", 85, 78, )
        val mockUser2 = Game(2, "Game 2", "Description 2", 90, 82, )

        val mockReview1 = Review(mockUser1, "This game is awesome!")
        val mockReview2 = Review(mockUser2, "Not bad, but could be better.")

        _reviews.value = listOf(mockReview1, mockReview2)
    }
*/
    fun addNewReview(review: ReviewItem) {
        val currentList = _reviews.value.orEmpty().toMutableList()
        currentList.add(review)
        _reviews.postValue(currentList)
    }
    fun changeRecommendState(item : ReviewItem?){
        if(item ==null) return
        val currentList = reviews.value ?: mutableListOf()
        if(currentList.contains(item)){
            val index = currentList.indexOf(item)
            currentList[index].isRecommended  =!  currentList[index].isRecommended
            println("review item recommen changed into : " + currentList[index].isRecommended)
        }
        _reviews.value = currentList
    }
    fun changeDesc(item : ReviewItem?, content : String){
        if(item ==null) return
        val currentList = reviews.value ?: mutableListOf()
        if(currentList.contains(item)){
            val index = currentList.indexOf(item)
            currentList[index].description = content
            println("review item desc changed into : " + currentList[index].description)
        }
        _reviews.value = currentList

    }

    fun getReviewList(): List<ReviewItem> {
        return reviews.value.orEmpty()
    }
}
