package tabacowang.me.moviego.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tabacowang.me.moviego.data.remote.model.CreditData
import tabacowang.me.moviego.data.remote.model.GenreList
import tabacowang.me.moviego.data.remote.model.MovieData
import tabacowang.me.moviego.data.remote.model.TmdbResponse

interface MovieApiService {
    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreList

    @GET("movie/{movieCategory}")
    suspend fun getMovieList(
        @Path("movieCategory") movieCategory: String,
        @Query("page") page: Int? = null
    ): TmdbResponse<MovieData>

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(@Path("movieId") movieId: String): CreditData

    @GET("movie/{movieId}/{movieCategory}")
    suspend fun getMovieDetailList(
        @Path("movieId") movieId: String,
        @Path("movieCategory") movieCategory: String,
        @Query("page") page: Int? = null
    ): TmdbResponse<MovieData>
}