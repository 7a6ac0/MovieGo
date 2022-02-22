package tabacowang.me.moviego.data.remote

import com.google.gson.annotations.SerializedName

data class TmdbResponse<T>(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
    @SerializedName("results")
    val results: List<T>?
)