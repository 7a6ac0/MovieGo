package tabacowang.me.moviego.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import tabacowang.me.moviego.ui.theme.MovieGoTheme

class MovieDetailFragment : Fragment() {
    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    private val viewModel: MovieDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MovieGoTheme {
                    Text("Hello")
                }
            }
        }
    }
}