package tabacowang.me.moviego.ui.all

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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.viewmodel.ext.android.viewModel
import tabacowang.me.moviego.data.remote.MovieData
import tabacowang.me.moviego.ui.home.BuildNormalItem
import tabacowang.me.moviego.ui.theme.MovieGoTheme
import tabacowang.me.moviego.ui.widget.BackPressedAppBar
import tabacowang.me.moviego.ui.widget.LoadingWidget
import tabacowang.me.moviego.util.MovieCategory

class MovieAllFragment : Fragment() {

    companion object {
        private const val ARGUMENT_MOVIE_CATEGORY = "ARGUMENT_MOVIE_CATEGORY"
        fun newInstance(movieCategory: MovieCategory) = MovieAllFragment().apply {
            arguments = bundleOf(ARGUMENT_MOVIE_CATEGORY to movieCategory)
        }
    }

    private val movieCategory: MovieCategory by lazy {
        requireArguments().getSerializable(ARGUMENT_MOVIE_CATEGORY) as MovieCategory
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
                        MovieAllWidget(moviePagingData = viewModel.getMoviePagingData(movieCategory))
                    }
                }
            }
        }
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
            items(movieItems) { movie ->
                movie?.let {
                    BuildNormalItem(movieData = it)
                }
            }

            movieItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            LoadingWidget(modifier = Modifier.fillParentMaxSize())
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