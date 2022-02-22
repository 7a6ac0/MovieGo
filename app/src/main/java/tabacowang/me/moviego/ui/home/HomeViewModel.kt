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
import tabacowang.me.moviego.data.remote.MovieData
import tabacowang.me.moviego.data.remote.TmdbResponse
import tabacowang.me.moviego.data.repo.MovieRepo

class HomeViewModel : ViewModel(), KoinComponent {
    private val movieRepo: MovieRepo by inject()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _nowPlayingMovies = MutableLiveData<TmdbResponse<MovieData>>()
    val nowPlayingMovies: LiveData<TmdbResponse<MovieData>> get() = _nowPlayingMovies

    private val _popularMovies = MutableLiveData<TmdbResponse<MovieData>>()
    val popularMovies: LiveData<TmdbResponse<MovieData>> get() = _popularMovies

    private val _topRatedMovies = MutableLiveData<TmdbResponse<MovieData>>()
    val topRatedMovies: LiveData<TmdbResponse<MovieData>> get() = _topRatedMovies

    private val _upcomingMovies = MutableLiveData<TmdbResponse<MovieData>>()
    val upcomingMovies: LiveData<TmdbResponse<MovieData>> get() = _upcomingMovies

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            val nowPlayingRequest = async { movieRepo.getNowPlayingMovies() }
            val popularRequest = async { movieRepo.getPopularMovies() }
            val topRatedRequest = async { movieRepo.getTopRatedMovies() }
            val upcomingRequest = async { movieRepo.getUpcomingMovies() }

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

    init {
        getNowPlayingMovies()
    }
}