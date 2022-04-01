package tabacowang.me.moviego.ui.all

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.viewmodel.ext.android.viewModel
import tabacowang.me.moviego.data.remote.model.MovieData
import tabacowang.me.moviego.data.remote.model.Review
import tabacowang.me.moviego.ui.MovieReviewClickListener
import tabacowang.me.moviego.ui.detail.MovieReviewWidget
import tabacowang.me.moviego.ui.widget.BuildNormalItem
import tabacowang.me.moviego.ui.widget.BuildNormalItemPlaceHolder
import tabacowang.me.moviego.ui.theme.MovieGoTheme
import tabacowang.me.moviego.ui.widget.BackPressedAppBar
import tabacowang.me.moviego.ui.widget.LoadingWidget
import tabacowang.me.moviego.util.MovieCategory

class MovieAllFragment : Fragment(), MovieReviewClickListener {

    companion object {
        private const val ARGUMENT_MOVIE_CATEGORY = "ARGUMENT_MOVIE_CATEGORY"
        private const val ARGUMENT_MOVIE_ID = "ARGUMENT_MOVIE_ID"
        fun newInstance(
            movieId: String? = null,
            movieCategory: MovieCategory
        ) = MovieAllFragment().apply {
            arguments = bundleOf(
                ARGUMENT_MOVIE_CATEGORY to movieCategory,
                ARGUMENT_MOVIE_ID to movieId
            )
        }
    }

    private val movieCategory: MovieCategory by lazy {
        requireArguments().getSerializable(ARGUMENT_MOVIE_CATEGORY) as MovieCategory
    }

    private val movieId: String by lazy {
        requireArguments().getString(ARGUMENT_MOVIE_ID, "")
    }

    private val viewModel: MovieAllViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MovieGoTheme {
                    Scaffold(
                        topBar = {
                            BackPressedAppBar(
                                title = stringResource(id = movieCategory.stringRes),
                                onClick = { requireActivity().onBackPressed() }
                            )
                        }
                    ) {
                        when (movieCategory) {
                            MovieCategory.MOW_PLAYING,
                            MovieCategory.UPCOMING,
                            MovieCategory.TOP_RATED,
                            MovieCategory.POPULAR -> {
                                MovieAllWidget(moviePagingData = viewModel.getMoviePagingData(movieCategory))
                            }
                            MovieCategory.SIMILAR,
                            MovieCategory.RECOMMENDATION -> {
                                MovieAllWidget(moviePagingData = viewModel.getMovieDetailPagingData(movieId, movieCategory))
                            }
                            MovieCategory.REVIEW -> {
                                MovieAllReviewWidget(
                                    moviePagingData = viewModel.getMovieReviewPagingData(movieId),
                                    reviewListener = this@MovieAllFragment
                                )
                            }
                        }

                    }
                }
            }
        }
    }

    override fun onMovieReviewClicked(review: Review) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(review.url))
        startActivity(browserIntent)
    }
}

@Composable
fun MovieAllWidget(
    moviePagingData: Flow<PagingData<MovieData>>
) {
    val movieItems = moviePagingData.collectAsLazyPagingItems()

    SwipeRefresh(
        state = rememberSwipeRefreshState(
            isRefreshing = movieItems.loadState.refresh == LoadState.Loading
        ),
        onRefresh = { movieItems.refresh() },
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            items(movieItems) { item ->
                item?.let {
                    BuildNormalItem(movieData = it)
                }
            }

            movieItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        items(count = 6) {
                            BuildNormalItemPlaceHolder(modifier = Modifier.placeholder(visible = true, highlight = PlaceholderHighlight.fade()))
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            LoadingWidget(modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieAllReviewWidget(
    moviePagingData: Flow<PagingData<Review>>,
    reviewListener: MovieReviewClickListener
) {
    val reviewItems = moviePagingData.collectAsLazyPagingItems()

    SwipeRefresh(
        state = rememberSwipeRefreshState(
            isRefreshing = reviewItems.loadState.refresh == LoadState.Loading
        ),
        onRefresh = { reviewItems.refresh() },
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            items(reviewItems) { item ->
                item?.let {
                    MovieReviewWidget(review = it, itemClickListener = reviewListener::onMovieReviewClicked)
                }
            }

            reviewItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        items(count = 6) {
                            BuildNormalItemPlaceHolder(modifier = Modifier.placeholder(visible = true, highlight = PlaceholderHighlight.fade()))
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            LoadingWidget(modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }
}
