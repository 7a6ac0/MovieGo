package tabacowang.me.moviego.ui.widget

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import tabacowang.me.moviego.BuildConfig
import tabacowang.me.moviego.R
import tabacowang.me.moviego.data.remote.model.Genre
import tabacowang.me.moviego.data.remote.model.MovieData
import tabacowang.me.moviego.ui.theme.genreBackground
import tabacowang.me.moviego.util.format
import java.util.*

@Composable
fun BuildBackdropItemPlaceHolder(
    width: Dp = getMovieItemFitWidth(),
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 5.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(width),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = modifier.aspectRatio(16f.div(9)))
            Text(
                text = stringResource(id = R.string.string_unknown),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).then(modifier)
            )
            Text(
                text = stringResource(id = R.string.string_unknown),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).then(modifier)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                repeat(3) {
                    Text(text = "動作", modifier = modifier)
                }
            }
        }
    }
}

@Composable
fun BuildBackdropItem(
    width: Dp = getMovieItemFitWidth(),
    movieData: MovieData,
    itemClickListener: ((MovieData) -> Unit)? = null
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 5.dp,
        modifier = Modifier
            .clickable { itemClickListener?.invoke(movieData) }
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(width),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = "${BuildConfig.IMAGE_API_ROOT}w500${movieData.backdropPath}",
                ),
                contentDescription = null,
                modifier = Modifier.aspectRatio(16f.div(9)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = movieData.title ?: stringResource(id = R.string.string_unknown),
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = movieData.releaseDate?.format() ?: stringResource(id = R.string.string_unknown),
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            if (!movieData.genreList.isNullOrEmpty()) {
                BuildMovieGenre(modifier = Modifier.padding(horizontal = 8.dp), genreList = movieData.genreList!!)
            }
        }
    }
}

@Composable
private fun getMovieItemFitWidth(): Dp {
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels.dp / LocalDensity.current.density - 16.dp
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels.dp / LocalDensity.current.density
    val orientation = LocalConfiguration.current.orientation
    return if (orientation == Configuration.ORIENTATION_PORTRAIT) screenWidth else screenHeight
}

@Composable
fun BuildPosterItemPlaceHolder(
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 5.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(200.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.aspectRatio(9f.div(16)).then(modifier))
            Text(
                text = stringResource(id = R.string.string_unknown),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).then(modifier)
            )
            Text(
                text = stringResource(id = R.string.string_unknown),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).then(modifier)
            )
            Text(text = "動作", modifier = Modifier.padding(horizontal = 8.dp).then(modifier))
        }
    }
}

@Composable
fun BuildPosterItem(
    movieData: MovieData,
    itemClickListener: ((MovieData) -> Unit)? = null
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 5.dp,
        modifier = Modifier
            .clickable { itemClickListener?.invoke(movieData) }
    ) {
        Box(
            modifier = Modifier.width(160.dp).aspectRatio(9f.div(16)),
        ) {
            Image(
                painter = rememberImagePainter(data = "${BuildConfig.IMAGE_API_ROOT}w500${movieData.posterPath}"),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = String.format("%.1f", movieData.voteAverage),
                    style = MaterialTheme.typography.h5.copy(color = Color.White),
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colors.primary,
                                    MaterialTheme.colors.primaryVariant
                                )
                            ),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
fun BuildNormalItemPlaceHolder(
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 5.dp
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
        ) {
            Box(modifier = Modifier.aspectRatio(1f).then(modifier))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(id = R.string.string_unknown),
                    modifier = Modifier.fillMaxWidth().then(modifier)
                )
                Text(
                    text = stringResource(id = R.string.string_unknown),
                    modifier = Modifier.fillMaxWidth().then(modifier)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) {
                        Text(text = "動作", modifier = modifier)
                    }
                }
            }
        }
    }
}

@Composable
fun BuildNormalItem(
    movieData: MovieData,
    itemClickListener: ((MovieData) -> Unit)? = null
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 5.dp,
        modifier = Modifier
            .clickable { itemClickListener?.invoke(movieData) }
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = "${BuildConfig.IMAGE_API_ROOT}w500${movieData.posterPath}"),
                contentDescription = null,
                modifier = Modifier.aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = movieData.title ?: stringResource(id = R.string.string_unknown),
                    style = MaterialTheme.typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = movieData.releaseDate?.format() ?: stringResource(id = R.string.string_unknown),
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                )
                if (!movieData.genreList.isNullOrEmpty()) {
                    BuildMovieGenre(genreList = movieData.genreList!!)
                }
            }
        }
    }
}

@Composable
fun BuildMovieGenre(modifier: Modifier = Modifier, genreList: List<Genre>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            genreList.forEach {
                Text(
                    text = it.name ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .background(color = genreBackground, shape = MaterialTheme.shapes.small)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MovieBackdropItemPreview() {
    BuildBackdropItem(
        320.dp,
        MovieData(
            id = "1234",
            title = "Spider-Man: No Way Home",
            overview = "spspspspspspsps",
            originalTitle = "Spider-Man: No Way Home",
            posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            releaseDate = Calendar.getInstance(),
            genreList = listOf(Genre(111, "動作"), Genre(222, "科幻")),
            genreIds = null,
            voteAverage = 4.2f,
            voteCount = 100,
            backdropPath = "/iQFcwSGbZXMkeyKrxbPnwnRo5fl.jpg"
        )
    )
}

@Composable
@Preview(showBackground = true)
fun MoviePosterItemPreview() {
    BuildPosterItem(
        MovieData(
            id = "1234",
            title = "Spider-Man: No Way Home",
            overview = "spspspspspspsps",
            originalTitle = "Spider-Man: No Way Home",
            posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            releaseDate = Calendar.getInstance(),
            genreList = listOf(Genre(111, "動作"), Genre(222, "科幻")),
            genreIds = null,
            voteAverage = 4.2f,
            voteCount = 100,
            backdropPath = "/iQFcwSGbZXMkeyKrxbPnwnRo5fl.jpg"
        )
    )
}

@Composable
@Preview(showBackground = true)
fun MovieNormalItemPreview() {
    BuildNormalItem(
        MovieData(
            id = "1234",
            title = "Spider-Man: No Way Home",
            overview = "spspspspspspsps",
            originalTitle = "Spider-Man: No Way Home",
            posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            releaseDate = Calendar.getInstance(),
            genreList = listOf(Genre(111, "動作"), Genre(222, "科幻")),
            genreIds = null,
            voteAverage = 4.2f,
            voteCount = 100,
            backdropPath = "/iQFcwSGbZXMkeyKrxbPnwnRo5fl.jpg"
        )
    )
}