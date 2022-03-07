package tabacowang.me.moviego.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tabacowang.me.moviego.data.repo.MovieApiRepo
import tabacowang.me.moviego.util.MovieCategory

abstract class MovieApiPagingSource<T : Any> : PagingSource<Int, T>(), KoinComponent {
    abstract suspend fun apiInvoke(page: Int): TmdbResponse<T>?

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val currentPage = params.key ?: 1
            val apiResult = apiInvoke(currentPage)
            apiResult?.results?.let { list ->
                val prevKey = if (currentPage == 1) null else currentPage - 1
                val nextKey = if (list.isEmpty()) null else currentPage + 1
                LoadResult.Page(
                    data = list,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } ?: let {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

class MovieAllPagingSource(
    private val movieCategory: MovieCategory
) : MovieApiPagingSource<MovieData>() {
    private val movieApiRepo: MovieApiRepo by inject()

    override suspend fun apiInvoke(page: Int): TmdbResponse<MovieData>? {
        val genreList = movieApiRepo.getGenreList() ?: emptyList()
        return movieApiRepo.getMovieList(movieCategory, page)?.also {
            it.results?.map { movieData -> movieData.filterGenreList(genreList) }
        }
    }
}