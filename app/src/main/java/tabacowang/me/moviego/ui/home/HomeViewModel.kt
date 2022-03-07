package tabacowang.me.moviego.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tabacowang.me.moviego.data.remote.Genre
import tabacowang.me.moviego.data.remote.MovieData
import tabacowang.me.moviego.data.remote.TmdbResponse
import tabacowang.me.moviego.data.remote.filterGenreList
import tabacowang.me.moviego.data.repo.MovieApiRepo
import tabacowang.me.moviego.util.MovieCategory

class HomeViewModel : ViewModel(), KoinComponent {
    private val movieApiRepo: MovieApiRepo by inject()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _nowPlayingMovies = MutableLiveData<TmdbResponse<MovieData>>()
    val nowPlayingMovies: LiveData<TmdbResponse<MovieData>> get() = _nowPlayingMovies

    private val _popularMovies = MutableLiveData<TmdbResponse<MovieData>>()
    val popularMovies: LiveData<TmdbResponse<MovieData>> get() = _popularMovies

    private val _topRatedMovies = MutableLiveData<TmdbResponse<MovieData>>()
    val topRatedMovies: LiveData<TmdbResponse<MovieData>> get() = _topRatedMovies

    private val _upcomingMovies = MutableLiveData<TmdbResponse<MovieData>>()
    val upcomingMovies: LiveData<TmdbResponse<MovieData>> get() = _upcomingMovies

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            val genreList = movieApiRepo.getGenreList() ?: emptyList()

            val nowPlayingRequest = async {
                movieApiRepo.getMovieList(MovieCategory.MOW_PLAYING)?.also {
                    it.results?.map { movieData -> movieData.filterGenreList(genreList) }
                }
            }
            val popularRequest = async {
                movieApiRepo.getMovieList(MovieCategory.POPULAR)?.also {
                    it.results?.map { movieData -> movieData.filterGenreList(genreList) }
                }
            }
            val topRatedRequest = async {
                movieApiRepo.getMovieList(MovieCategory.TOP_RATED)?.also {
                    it.results?.map { movieData -> movieData.filterGenreList(genreList) }
                }
            }
            val upcomingRequest = async {
                movieApiRepo.getMovieList(MovieCategory.UPCOMING)?.also {
                    it.results?.map { movieData -> movieData.filterGenreList(genreList) }
                }
            }

            val (nowPlayingResult, popularResult, topRatedResult, upcomingResult) = listOf(
                nowPlayingRequest,
                popularRequest,
                topRatedRequest,
                upcomingRequest
            ).awaitAll()


            _nowPlayingMovies.value = nowPlayingResult
            _popularMovies.value = popularResult
            _topRatedMovies.value = topRatedResult
            _upcomingMovies.value = upcomingResult

            _isLoading.value = false
        }
    }
}