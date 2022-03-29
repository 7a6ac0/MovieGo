package tabacowang.me.moviego.ui

import tabacowang.me.moviego.data.remote.model.MovieData
import tabacowang.me.moviego.util.MovieCategory

interface MovieGoClickListener {
    fun onButtonSeeAllClicked(movieCategory: MovieCategory)
    fun onMovieItemClicked(movieData: MovieData)
}