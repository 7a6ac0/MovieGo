package tabacowang.me.moviego.data.remote.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class MovieData(
    @SerializedName("id")
    val id: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("release_date")
    val releaseDate: Calendar?,
    @SerializedName("genres")
    var genreList: List<Genre>?,
    @SerializedName("vote_average")
    val voteAverage: Float?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?
)

data class GenreList(
    @SerializedName("genres")
    val genreList: List<Genre>?
)

data class Genre(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)

data class Review(
    @SerializedName("author")
    val author: String?,
    @SerializedName("author_details")
    val authorDetail: AuthorDetail?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("updated_at")
    val updatedAt: Calendar?,
    @SerializedName("url")
    val url: String?
)

data class AuthorDetail(
    @SerializedName("name")
    val name: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("rating")
    val rating: Float?
)

fun MovieData.filterGenreList(genreList: List<Genre>) {
    this.genreList = genreList.filter { genre ->  this.genreIds?.contains(genre.id) ?: false}
}