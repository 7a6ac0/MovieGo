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
import tabacowang.me.moviego.data.remote.model.CreditData
import tabacowang.me.moviego.data.remote.model.MovieData
import tabacowang.me.moviego.data.remote.model.TmdbResponse
import tabacowang.me.moviego.data.remote.model.filterGenreList
import tabacowang.me.moviego.data.repo.MovieApiRepo
import tabacowang.me.moviego.util.MovieCategory

class MovieDetailViewModel : ViewModel(), KoinComponent {
    private val movieApiRepo: MovieApiRepo by inject()

    val isLoading: MutableState<Boolean> = mutableStateOf(true)
    val credits: MutableState<CreditData?> = mutableStateOf(null)
    val similarMovies: MutableState<TmdbResponse<MovieData>?> = mutableStateOf(null)
    val recommendationMovies: MutableState<TmdbResponse<MovieData>?> = mutableStateOf(null)

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

            val (creditResult, similarMovieResult, recommendationMovieResult) = listOf(
                creditRequest,
                similarMovieRequest,
                recommendationMovieRequest
            ).awaitAll()

            credits.value = creditResult as? CreditData
            similarMovies.value = similarMovieResult as? TmdbResponse<MovieData>
            recommendationMovies.value = recommendationMovieResult as? TmdbResponse<MovieData>

            isLoading.value = false
        }
    }
}