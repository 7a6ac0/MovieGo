package tabacowang.me.moviego.data.repo

import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tabacowang.me.moviego.data.local.GenreModel
import tabacowang.me.moviego.data.remote.Genre
import tabacowang.me.moviego.data.remote.MovieApiService
import tabacowang.me.moviego.data.remote.MovieData
import tabacowang.me.moviego.data.remote.TmdbResponse

interface MovieApiRepo {
    suspend fun getGenreList(): List<Genre>?
    suspend fun getNowPlayingMovies(): TmdbResponse<MovieData>?
    suspend fun getPopularMovies(): TmdbResponse<MovieData>?
    suspend fun getTopRatedMovies(): TmdbResponse<MovieData>?
    suspend fun getUpcomingMovies(): TmdbResponse<MovieData>?
}

class MovieApiApiRepoImpl : BaseRepo(), MovieApiRepo, KoinComponent {
    private val movieApiService: MovieApiService by inject()
    private val movieDbRepo: MovieDbRepo by inject()

    override suspend fun getGenreList() = coroutineApiCall(Dispatchers.IO) {
        val genreModelList = movieDbRepo.getGenreList() ?: emptyList()
        if (genreModelList.isEmpty()) {
            val genreList = movieApiService.getGenreList().genreList
            genreList?.forEach { genre ->
                genre.id
                    ?.let { GenreModel(id = it, name = genre.name) }
                    ?.let { movieDbRepo.insertGenre(it) }
            }
            genreList
        } else {
            genreModelList.map {
                Genre(id = it.id, name = it.name)
            }
        }
    }

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