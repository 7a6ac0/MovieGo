package tabacowang.me.moviego.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import org.koin.androidx.viewmodel.ext.android.viewModel
import tabacowang.me.moviego.R
import tabacowang.me.moviego.data.remote.model.MovieData
import tabacowang.me.moviego.ui.MovieGoClickListener
import tabacowang.me.moviego.ui.all.MovieAllFragment
import tabacowang.me.moviego.ui.detail.MovieDetailFragment
import tabacowang.me.moviego.ui.theme.MovieGoTheme
import tabacowang.me.moviego.ui.widget.HeaderWidget
import tabacowang.me.moviego.util.MovieCategory
import tabacowang.me.moviego.util.openFragment

class MovieGoFragment : Fragment(), MovieGoClickListener {

    companion object {
        fun newInstance() = MovieGoFragment()
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
                        MovieGoMainWidget(viewModel = viewModel, listener = this@MovieGoFragment)
                    }
                }
            }
        }
    }

    override fun onButtonSeeAllClicked(movieCategory: MovieCategory) {
        requireActivity().openFragment(MovieAllFragment.newInstance(movieCategory = movieCategory), true)
    }

    override fun onMovieItemClicked(movieData: MovieData) {
        requireActivity().openFragment(MovieDetailFragment.newInstance(movieData), true)
    }
}

@Composable
fun MovieGoMainWidget(
    viewModel: HomeViewModel,
    listener: MovieGoClickListener
) {
    val isLoading by viewModel.isLoading.observeAsState(true)
    val nowPlaying by viewModel.nowPlayingMovies.observeAsState()
    val popular by viewModel.popularMovies.observeAsState()
    val topRated by viewModel.topRatedMovies.observeAsState()
    val upcoming by viewModel.upcomingMovies.observeAsState()

    if (isLoading) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .verticalScroll(state = rememberScrollState(), enabled = false)
        ) {
            HeaderWidget(
                title = stringResource(id = R.string.category_now_playing),
                showPlaceHolder = true,
                onClickListener = {}
            )
            BuildBackdropItemPlaceHolder(
                modifier = Modifier.placeholder(visible = true, highlight = PlaceholderHighlight.fade())
            )
            HeaderWidget(
                title = stringResource(id = R.string.category_popular),
                showPlaceHolder = true,
                onClickListener = {}
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.horizontalScroll(state = rememberScrollState(), enabled = false)
            ) {
                repeat(2) {
                    BuildPosterItemPlaceHolder(
                        modifier = Modifier.placeholder(visible = true, highlight = PlaceholderHighlight.fade())
                    )
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
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
                        movieList = nowPlaying?.results ?: emptyList(),
                        itemClickListener = listener::onMovieItemClicked
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
                        movieList = popular?.results ?: emptyList(),
                        itemClickListener = listener::onMovieItemClicked
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
                        movieList = topRated?.results ?: emptyList(),
                        itemClickListener = listener::onMovieItemClicked
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
                    BuildNormalItem(
                        movieData = movie,
                        itemClickListener = listener::onMovieItemClicked
                    )
                }
            }
        }
    }
}

@Composable
fun BuildMovieCarousel(
    itemType: MovieItemType = MovieItemType.BACKDROP,
    movieList: List<MovieData>,
    itemClickListener: ((MovieData) -> Unit)? = null
) {
    when (itemType) {
        MovieItemType.BACKDROP -> {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(movieList) { movie ->
                    BuildBackdropItem(
                        movieData = movie,
                        itemClickListener = itemClickListener
                    )
                }
            }
        }
        MovieItemType.POSTER -> {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(movieList) { movie ->
                    BuildPosterItem(
                        movieData = movie,
                        itemClickListener = itemClickListener
                    )
                }
            }
        }
    }
}
