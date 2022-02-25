package tabacowang.me.moviego.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import tabacowang.me.moviego.BuildConfig
import tabacowang.me.moviego.data.remote.Genre
import tabacowang.me.moviego.data.remote.MovieData
import tabacowang.me.moviego.ui.theme.CornerDimensions
import tabacowang.me.moviego.util.format
import java.util.*

@Composable
fun BuildMovieItem(
    width: Dp,
    movie: MovieData
) {
    Card(
        shape = RoundedCornerShape(CornerDimensions.cardCorner),
        elevation = 5.dp,
        modifier = Modifier
            .clickable {  }
    ) {
        Column(modifier = Modifier
            .padding(bottom = 8.dp)
            .width(width)
        ) {
            Image(
                painter = rememberImagePainter(data = "${BuildConfig.IMAGE_API_ROOT}w500/${movie.backdropPath}"),
                contentDescription = null,
                modifier = Modifier.aspectRatio(16f.div(9)),
                contentScale = ContentScale.Crop
            )
            Text(
                movie.title ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Text(
                movie.releaseDate?.format() ?: "",
                maxLines = 1,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            if (!movie.genreList.isNullOrEmpty()) {
                BuildMovieGenre(movie.genreList!!)
            }
        }
    }
}

@Composable
fun BuildMovieGenre(genreList: List<Genre>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
    ) {
        genreList.forEach {
            Text(
                text = it.name ?: "",
                modifier = Modifier
                    .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(4.dp)
            )
        }
    }
}

@Composable
@Preview
fun MovieItemPreview() {
    BuildMovieItem(
        320.dp,
        MovieData(
            id = "1234",
            title = "Spider-Man: No Way Home",
            originalTitle = "Spider-Man: No Way Home",
            posterPath = "${BuildConfig.IMAGE_API_ROOT}w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            releaseDate = Calendar.getInstance(),
            genreList = listOf(Genre(111, "動作"), Genre(222, "科幻")),
            genreIds = null,
            voteAverage = 4.2f,
            voteCount = 100,
            backdropPath = "${BuildConfig.IMAGE_API_ROOT}w500/iQFcwSGbZXMkeyKrxbPnwnRo5fl.jpg"
        )
    )
}