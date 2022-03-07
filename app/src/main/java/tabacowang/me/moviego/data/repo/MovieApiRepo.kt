package tabacowang.me.moviego.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tabacowang.me.moviego.data.local.GenreModel
import tabacowang.me.moviego.data.remote.*
import tabacowang.me.moviego.util.MovieCategory

interface MovieApiRepo {
    suspend fun getGenreList(): List<Genre>?
    suspend fun getMovieList(
        movieCategory: MovieCategory,
        page: Int? = null
    ): TmdbResponse<MovieData>?
    fun getMoviePagingData(
        movieCategory: MovieCategory,
        pageSize: Int = 10
    ): Flow<PagingData<MovieData>>
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

    override suspend fun getMovieList(movieCategory: MovieCategory, page: Int?) = coroutineApiCall(Dispatchers.IO) {
        movieApiService.getMovieList(movieCategory.category, page)
    }

    override fun getMoviePagingData(
        movieCategory: MovieCategory,
        pageSize: Int
    ): Flow<PagingData<MovieData>> {
        val pager = Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                MovieAllPagingSource(movieCategory = movieCategory)
            }
        )

        return pager.flow
    }
}