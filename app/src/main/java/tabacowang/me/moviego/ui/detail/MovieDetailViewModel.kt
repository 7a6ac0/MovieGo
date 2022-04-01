package tabacowang.me.moviego.ui.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tabacowang.me.moviego.data.remote.model.*
import tabacowang.me.moviego.data.repo.MovieApiRepo
import tabacowang.me.moviego.util.MovieCategory

class MovieDetailViewModel : ViewModel(), KoinComponent {
    private val movieApiRepo: MovieApiRepo by inject()

    val isLoading: MutableState<Boolean> = mutableStateOf(true)
    val credits: MutableState<CreditData?> = mutableStateOf(null)
    val similarMovies: MutableState<TmdbResponse<MovieData>?> = mutableStateOf(null)
    val recommendationMovies: MutableState<TmdbResponse<MovieData>?> = mutableStateOf(null)
    val reviews: MutableState<TmdbResponse<Review>?> = mutableStateOf(null)

    fun getMovieDetail(movieId: String) {
        viewModelScope.launch {
            isLoading.value = true

            val creditRequest = async { movieApiRepo.getMovieCredits(movieId) }
            val similarMovieRequest = async { movieApiRepo.getMovieDetailList(movieId, MovieCategory.SIMILAR) }
            val recommendationMovieRequest = async {
                val genreList = movieApiRepo.getGenreList() ?: emptyList()
                movieApiRepo.getMovieDetailList(movieId, MovieCategory.RECOMMENDATION)?.also {
                    it.results?.map { movieData -> movieData.filterGenreList(genreList) }
                }
            }
            val reviewRequest = async { movieApiRepo.getMovieReviews(movieId = movieId) }

            val (creditResult, similarMovieResult, recommendationMovieResult, reviewResult) = listOf(
                creditRequest,
                similarMovieRequest,
                recommendationMovieRequest,
                reviewRequest
            ).awaitAll()

            credits.value = creditResult as? CreditData
            similarMovies.value = similarMovieResult as? TmdbResponse<MovieData>
            recommendationMovies.value = recommendationMovieResult as? TmdbResponse<MovieData>
            reviews.value = reviewResult as? TmdbResponse<Review>

            isLoading.value = false
        }
    }
}