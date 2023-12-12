import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.questlog.R

class ReviewPopupFragment : Fragment() {

    interface ReviewListener {
        fun onReviewSubmitted(reviewText: String)
    }

    private var reviewListener: ReviewListener? = null

    fun setReviewListener(listener: ReviewListener) {
        reviewListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_review_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSubmitReview.setOnClickListener {
            val reviewText = editTextReview.text.toString()
            reviewListener?.onReviewSubmitted(reviewText)
            dismiss()
        }
    }
}
