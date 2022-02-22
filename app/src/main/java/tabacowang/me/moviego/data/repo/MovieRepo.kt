package tabacowang.me.moviego.data.repo

import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tabacowang.me.moviego.data.remote.MovieApiService
import tabacowang.me.moviego.data.remote.MovieData
import tabacowang.me.moviego.data.remote.TmdbResponse
import tabacowang.me.moviego.util.Resource

interface MovieRepo {
    suspend fun getNowPlayingMovies(): TmdbResponse<MovieData>?
    suspend fun getPopularMovies(): TmdbResponse<MovieData>?
    suspend fun getTopRatedMovies(): TmdbResponse<MovieData>?
    suspend fun getUpcomingMovies(): TmdbResponse<MovieData>?
}

class MovieRepoImpl : BaseRepo(), MovieRepo, KoinComponent {
    private val movieApiService: MovieApiService by inject()

    override suspend fun getNowPlayingMovies() = coroutineApiCall(Dispatchers.IO) {
        movieApiService.getNowPlayingMovies()
    }

    override suspend fun getPopularMovies() = coroutineApiCall(Dispatchers.IO) {
        movieApiService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies() = coroutineApiCall(Dispatchers.IO) {
        movieApiService.getTopRatedMovies()
    }

    override suspend fun getUpcomingMovies() = coroutineApiCall(Dispatchers.IO) {
        movieApiService.getUpcomingMovies()
    }
}