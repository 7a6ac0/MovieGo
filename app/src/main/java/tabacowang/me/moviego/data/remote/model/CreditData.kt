package tabacowang.me.moviego.data.remote.model

import com.google.gson.annotations.SerializedName

data class CreditData(
    @SerializedName("cast")
    val cast: List<Cast>?
)

data class Cast(
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("profile_path")
    val profilePath: String?
)

