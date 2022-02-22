package tabacowang.me.moviego.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import tabacowang.me.moviego.BuildConfig
import tabacowang.me.moviego.data.remote.MovieData
import tabacowang.me.moviego.ui.theme.CornerDimensions
import tabacowang.me.moviego.util.format
import java.util.*

@Composable
fun BuildMovieItem(movie: MovieData) {
    Card(
        shape = RoundedCornerShape(CornerDimensions.cardCorner),
        elevation = 5.dp,
        modifier = Modifier
            .clickable {  }
    ) {
        Column(modifier = Modifier
            .padding(bottom = 8.dp)
            .width(320.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = "${BuildConfig.IMAGE_API_ROOT}w500/${movie.backdropPath}"),
                contentDescription = null,
                modifier = Modifier.aspectRatio(16f.div(9)),
                contentScale = ContentScale.Crop
            )
            Text(
                movie.title ?: "",
                modifier = Modifier.padding(8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                movie.releaseDate?.format() ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
@Preview
fun MovieItemPreview() {
    BuildMovieItem(
        MovieData(
            id = "1234",
            title = "Spider-Man: No Way Home",
            posterPath = "${BuildConfig.IMAGE_API_ROOT}w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            releaseDate = Calendar.getInstance(),
            genreList = null,
            genreIds = null,
            voteAverage = 4.2f,
            voteCount = 100,
            backdropPath = "${BuildConfig.IMAGE_API_ROOT}w500/iQFcwSGbZXMkeyKrxbPnwnRo5fl.jpg"
        )
    )
}