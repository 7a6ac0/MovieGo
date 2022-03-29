package tabacowang.me.moviego.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import tabacowang.me.moviego.R
import tabacowang.me.moviego.data.remote.model.MovieData
import tabacowang.me.moviego.ui.MovieGoClickListener
import tabacowang.me.moviego.ui.all.MovieAllFragment
import tabacowang.me.moviego.ui.home.BuildBackdropItem
import tabacowang.me.moviego.ui.home.BuildPosterItem
import tabacowang.me.moviego.ui.theme.MovieGoTheme
import tabacowang.me.moviego.ui.widget.BackPressedAppBar
import tabacowang.me.moviego.ui.widget.HeaderWidget
import tabacowang.me.moviego.ui.widget.LoadingWidget
import tabacowang.me.moviego.util.MovieCategory
import tabacowang.me.moviego.util.openFragment

class MovieDetailFragment : Fragment(), MovieGoClickListener {
    companion object {
        private const val ARGUMENT_MOVIE_DATA = "ARGUMENT_MOVIE_DATA"
        fun newInstance(movieData: MovieData) = MovieDetailFragment().apply {
            arguments = bundleOf(ARGUMENT_MOVIE_DATA to Gson().toJson(movieData))
        }
    }

    private val movieData: MovieData by lazy {
        Gson().fromJson(requireArguments().getString(ARGUMENT_MOVIE_DATA), MovieData::class.java)
    }
    private val viewModel: MovieDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getMovieDetail(movieData.id)
        }
    }

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
                                title = movieData.title ?: stringResource(id = R.string.string_unknown),
                                onClick = { requireActivity().onBackPressed() }
                            )
                        }
                    ) {
                        val isLoading = viewModel.isLoading.value
                        if (isLoading) {
                            LoadingWidget(modifier = Modifier.fillMaxSize())
                        } else {
                            MovieDetailMainWidget(movieData, viewModel, this@MovieDetailFragment)
                        }
                    }
                }
            }
        }
    }

    override fun onButtonSeeAllClicked(movieCategory: MovieCategory) {
        requireActivity().openFragment(MovieAllFragment.newInstance(
            movieId = movieData.id,
            movieCategory = movieCategory
        ), true)
    }

    override fun onMovieItemClicked(movieData: MovieData) {
        requireActivity().openFragment(newInstance(movieData), false)
    }
}

@Composable
fun MovieDetailMainWidget(
    movieData: MovieData,
    viewModel: MovieDetailViewModel,
    listener: MovieGoClickListener
) {
    val creditData = viewModel.credits.value
    val similarMovies = viewModel.similarMovies.value
    val recommendationMovies = viewModel.recommendationMovies.value

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 8.dp)
    ) {
        item {
            MovieDetailInfoWidget(movieData = movieData)
        }

        if (creditData?.cast?.isNotEmpty() == true) {
            item {
                HeaderWidget(title = stringResource(id = R.string.movie_detail_cast))
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(creditData.cast) { cast ->
                        MovieCastWidget(cast = cast)
                    }
                }
            }
        }

        if (similarMovies?.results?.isNotEmpty() == true) {
            item {
                HeaderWidget(title = stringResource(id = R.string.movie_detail_similar)) {
                    listener.onButtonSeeAllClicked(MovieCategory.SIMILAR)
                }
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(similarMovies.results) { movie ->
                        BuildPosterItem(
                            movieData = movie,
                            itemClickListener = listener::onMovieItemClicked
                        )
                    }
                }
            }
        }

        if (recommendationMovies?.results?.isNotEmpty() == true) {
            item {
                HeaderWidget(title = stringResource(id = R.string.movie_detail_recommendation)) {
                    listener.onButtonSeeAllClicked(MovieCategory.RECOMMENDATION)
                }
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(recommendationMovies.results) { movie ->
                        BuildBackdropItem(
                            movieData = movie,
                            itemClickListener = listener::onMovieItemClicked
                        )
                    }
                }
            }
        }
    }
}