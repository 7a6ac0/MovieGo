package tabacowang.me.moviego.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tabacowang.me.moviego.data.remote.model.*

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

    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Path("movieId") movieId: String,
        @Query("page") page: Int? = null
    ): TmdbResponse<Review>
}