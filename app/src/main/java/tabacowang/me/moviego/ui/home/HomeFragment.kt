package tabacowang.me.moviego.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import tabacowang.me.moviego.R
import tabacowang.me.moviego.data.remote.MovieData
import tabacowang.me.moviego.ui.all.MovieAllFragment
import tabacowang.me.moviego.ui.theme.MovieGoTheme
import tabacowang.me.moviego.ui.widget.HeaderWidget
import tabacowang.me.moviego.ui.widget.LoadingWidget
import tabacowang.me.moviego.util.MovieCategory
import tabacowang.me.moviego.util.openFragment

class HomeFragment : Fragment(), HomeClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MovieGoTheme {
                    Scaffold {
                        MovieGoMainWidget(viewModel = viewModel, listener = this@HomeFragment)
                    }
                }
            }
        }
    }

    override fun onButtonSeeAllClicked(movieCategory: MovieCategory) {
        requireActivity().openFragment(MovieAllFragment.newInstance(movieCategory), true)
    }
}

@Composable
fun MovieGoMainWidget(
    viewModel: HomeViewModel,
    listener: HomeClickListener
) {
    val isLoading by viewModel.isLoading.observeAsState(true)
    val nowPlaying by viewModel.nowPlayingMovies.observeAsState()
    val popular by viewModel.popularMovies.observeAsState()
    val topRated by viewModel.topRatedMovies.observeAsState()
    val upcoming by viewModel.upcomingMovies.observeAsState()

    if (isLoading) {
        LoadingWidget(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            if (nowPlaying?.results?.isNotEmpty() == true) {
                item {
                    HeaderWidget(title = stringResource(id = R.string.category_now_playing)) {
                        listener.onButtonSeeAllClicked(MovieCategory.MOW_PLAYING)
                    }
                }
                item {
                    BuildMovieCarousel(
                        movieList = nowPlaying?.results ?: emptyList()
                    )
                }
            }

            if (popular?.results?.isNotEmpty() == true) {
                item {
                    HeaderWidget(title = stringResource(id = R.string.category_popular)) {
                        listener.onButtonSeeAllClicked(MovieCategory.POPULAR)
                    }
                }
                item {
                    BuildMovieCarousel(
                        itemType = MovieItemType.POSTER,
                        movieList = popular?.results ?: emptyList()
                    )
                }
            }

            if (topRated?.results?.isNotEmpty() == true) {
                item {
                    HeaderWidget(title = stringResource(id = R.string.category_top_rated)) {
                        listener.onButtonSeeAllClicked(MovieCategory.TOP_RATED)
                    }
                }
                item {
                    BuildMovieCarousel(
                        movieList = topRated?.results ?: emptyList()
                    )
                }
            }

            if (upcoming?.results?.isNotEmpty() == true) {
                item {
                    HeaderWidget(title = stringResource(id = R.string.category_upcoming)) {
                        listener.onButtonSeeAllClicked(MovieCategory.UPCOMING)
                    }
                }
                items(upcoming?.results ?: emptyList()) { movie ->
                    BuildNormalItem(movie = movie)
                }
            }
        }
    }
}

@Composable
fun BuildMovieCarousel(itemType: MovieItemType = MovieItemType.BACKDROP, movieList: List<MovieData>) {
    when (itemType) {
        MovieItemType.BACKDROP -> {
            val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels.dp / LocalDensity.current.density - 16.dp
            val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels.dp / LocalDensity.current.density
            val orientation = LocalConfiguration.current.orientation
            val itemWidth = if (orientation == Configuration.ORIENTATION_PORTRAIT) screenWidth else screenHeight

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(movieList) { movie ->
                    BuildBackdropItem(width = itemWidth, movie = movie)
                }
            }
        }
        MovieItemType.POSTER -> {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(movieList) { movie ->
                    BuildPosterItem(movie = movie)
                }
            }
        }
    }
}
