import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.questlog.Game
import com.example.questlog.Review



class ReviewViewModel : ViewModel() {

    private val _reviews = MutableLiveData<List<Review>>()

    val reviews: LiveData<List<Review>> get() = _reviews

    init {
        // Populate with mock data
        val mockUser1 = Game(1, "Game 1", "Description 1", 85, 78, )
        val mockUser2 = Game(2, "Game 2", "Description 2", 90, 82, )

        val mockReview1 = Review(mockUser1, "This game is awesome!")
        val mockReview2 = Review(mockUser2, "Not bad, but could be better.")

        _reviews.value = listOf(mockReview1, mockReview2)
    }

    fun addNewReview(review: Review) {
        val currentList = _reviews.value.orEmpty().toMutableList()
        currentList.add(review)
        _reviews.postValue(currentList)
    }

    fun getReviewList(): List<Review> {
        return reviews.value.orEmpty()
    }
}
