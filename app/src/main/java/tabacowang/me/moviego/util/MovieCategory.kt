package tabacowang.me.moviego.util

import androidx.annotation.StringRes
import tabacowang.me.moviego.R

enum class MovieCategory(val category: String, @StringRes val stringRes: Int) {
    MOW_PLAYING("now_playing", R.string.category_now_playing),
    POPULAR("popular", R.string.category_popular),
    TOP_RATED("top_rated", R.string.category_top_rated),
    UPCOMING("upcoming", R.string.category_upcoming)
}