package tabacowang.me.moviego.ui.home

import tabacowang.me.moviego.util.MovieCategory

interface HomeClickListener {
    fun onButtonSeeAllClicked(movieCategory: MovieCategory)
}