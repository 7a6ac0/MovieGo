package tabacowang.me.moviego.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
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
import tabacowang.me.moviego.ui.theme.MovieGoTheme
import tabacowang.me.moviego.ui.widget.BuildMovieItem
import tabacowang.me.moviego.ui.widget.HeaderWidget

class HomeFragment : Fragment() {

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
                    MovieGoMainWidget(viewModel)
                }
            }
        }
    }
}

@Composable
fun MovieGoMainWidget(
    viewModel: HomeViewModel
) {
    val isLoading by viewModel.isLoading.observeAsState(true)
    val nowPlaying by viewModel.nowPlayingMovies.observeAsState()
    val popular by viewModel.popularMovies.observeAsState()
    val topRated by viewModel.topRatedMovies.observeAsState()
    val upcoming by viewModel.upcomingMovies.observeAsState()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            BuildMovieCarousel(title = stringResource(id = R.string.category_now_playing), movieList = nowPlaying?.results ?: emptyList())
            BuildMovieCarousel(title = stringResource(id = R.string.category_popular), movieList = popular?.results ?: emptyList())
            BuildMovieCarousel(title = stringResource(id = R.string.category_top_rated), movieList = topRated?.results ?: emptyList())
            BuildMovieCarousel(title = stringResource(id = R.string.category_upcoming), movieList = upcoming?.results ?: emptyList())
        }
    }
}

@Composable
fun BuildMovieCarousel(title: String, movieList: List<MovieData>) {
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels.dp / LocalDensity.current.density - 16.dp
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels.dp / LocalDensity.current.density
    val orientation = LocalConfiguration.current.orientation
    val itemWidth = if (orientation == Configuration.ORIENTATION_PORTRAIT) screenWidth else screenHeight

    Spacer(modifier = Modifier.padding(top = 4.dp))
    HeaderWidget(title = title) {

    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(movieList) { movie ->
            BuildMovieItem(width = itemWidth, movie = movie)
        }
    }
}
