package tabacowang.me.moviego.ui

import tabacowang.me.moviego.data.remote.model.Review

interface MovieReviewClickListener {
    fun onMovieReviewClicked(review: Review)
}