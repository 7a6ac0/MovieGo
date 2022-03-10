package tabacowang.me.moviego.ui.detail

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
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import tabacowang.me.moviego.R
import tabacowang.me.moviego.data.remote.MovieData
import tabacowang.me.moviego.ui.theme.MovieGoTheme
import tabacowang.me.moviego.ui.widget.BackPressedAppBar

class MovieDetailFragment : Fragment() {
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
                        MovieDetailMainWidget(movieData, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetailMainWidget(
    movieData: MovieData,
    viewModel: MovieDetailViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {

    }
}