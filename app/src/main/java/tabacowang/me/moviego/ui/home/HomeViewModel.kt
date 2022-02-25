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
import tabacowang.me.moviego.data.repo.MovieApiRepo

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

            val nowPlayingRequest = async { movieApiRepo.getNowPlayingMovies() }
            val popularRequest = async { movieApiRepo.getPopularMovies() }
            val topRatedRequest = async { movieApiRepo.getTopRatedMovies() }
            val upcomingRequest = async { movieApiRepo.getUpcomingMovies() }

            val (nowPlayingResult, popularResult, topRatedResult, upcomingResult) = listOf(
                nowPlayingRequest,
                popularRequest,
                topRatedRequest,
                upcomingRequest
            ).awaitAll()

            if (genreList.isNotEmpty()) {
                findGenreNameList(genreList, nowPlayingResult?.results)
                findGenreNameList(genreList, popularResult?.results)
                findGenreNameList(genreList, topRatedResult?.results)
                findGenreNameList(genreList, upcomingResult?.results)
            }

            _nowPlayingMovies.value = nowPlayingResult
            _popularMovies.value = popularResult
            _topRatedMovies.value = topRatedResult
            _upcomingMovies.value = upcomingResult

            _isLoading.value = false
        }
    }

    private fun findGenreNameList(genreList: List<Genre>, movieDatas: List<MovieData>?) {
        if (!movieDatas.isNullOrEmpty()) {
            movieDatas.forEach { movieData ->
                movieData.genreList = genreList.filter { movieData.genreIds?.contains(it.id) ?: false}
            }
        }
    }
}