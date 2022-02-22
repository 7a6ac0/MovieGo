package tabacowang.me.moviego.data.remote

import retrofit2.http.GET

interface MovieApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): TmdbResponse<MovieData>

    @GET("movie/popular")
    suspend fun getPopularMovies(): TmdbResponse<MovieData>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): TmdbResponse<MovieData>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): TmdbResponse<MovieData>
}