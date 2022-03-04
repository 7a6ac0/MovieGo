package tabacowang.me.moviego.data.repo

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tabacowang.me.moviego.data.local.GenreModel
import tabacowang.me.moviego.data.local.MovieGoDao

interface MovieDbRepo {
    suspend fun insertGenre(genre: GenreModel)
    suspend fun getGenreList(): List<GenreModel>?
}

class MovieDbRepoImpl : BaseRepo(), MovieDbRepo, KoinComponent {
    private val movieGoDao: MovieGoDao by inject()

    override suspend fun insertGenre(genre: GenreModel) {
        movieGoDao.insertGenre(genre)
    }

    override suspend fun getGenreList(): List<GenreModel>? {
        return movieGoDao.getGenreList()
    }
}