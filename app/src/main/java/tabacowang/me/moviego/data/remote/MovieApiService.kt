package tabacowang.me.moviego.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreList

    @GET("movie/{movieCategory}")
    suspend fun getMovieList(
        @Path("movieCategory") movieCategory: String,
        @Query("page") page: Int? = null
    ): TmdbResponse<MovieData>
}