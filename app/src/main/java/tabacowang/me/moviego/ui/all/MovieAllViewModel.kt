package tabacowang.me.moviego.ui.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tabacowang.me.moviego.data.remote.model.MovieData
import tabacowang.me.moviego.data.repo.MovieApiRepo
import tabacowang.me.moviego.util.MovieCategory

class MovieAllViewModel : ViewModel(), KoinComponent {
    private val movieApiRepo: MovieApiRepo by inject()

    fun getMoviePagingData(movieCategory: MovieCategory): Flow<PagingData<MovieData>> {
        return movieApiRepo.getMoviePagingData(movieCategory = movieCategory).cachedIn(viewModelScope)
    }

    fun getMovieDetailPagingData(movieId: String, movieCategory: MovieCategory): Flow<PagingData<MovieData>> {
        return movieApiRepo.getMovieDetailPagingData(
            movieId = movieId,
            movieCategory = movieCategory
        ).cachedIn(viewModelScope)
    }
}