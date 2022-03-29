package tabacowang.me.moviego.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.gowtham.ratingbar.RatingBar
import tabacowang.me.moviego.BuildConfig
import tabacowang.me.moviego.R
import tabacowang.me.moviego.data.remote.model.Cast
import tabacowang.me.moviego.data.remote.model.MovieData
import tabacowang.me.moviego.ui.home.BuildMovieGenre
import tabacowang.me.moviego.util.format

@Composable
fun MovieDetailInfoWidget(movieData: MovieData) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = "${BuildConfig.IMAGE_API_ROOT}w500${movieData.backdropPath}"),
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
            text = "    ∟${movieData.originalTitle ?: stringResource(id = R.string.string_unknown)}",
            style = MaterialTheme.typography.subtitle1,
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
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val realVoteAverage = movieData.voteAverage ?: 0f
            RatingBar(
                value = realVoteAverage / 2,
                onValueChange = {},
                onRatingChanged = {},
            )
            Text(
                text = "${movieData.voteCount ?: 0} votes ($realVoteAverage star)",
                style = MaterialTheme.typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
        }
        if (!movieData.genreList.isNullOrEmpty()) {
            BuildMovieGenre(modifier = Modifier.padding(horizontal = 8.dp), genreList = movieData.genreList!!)
        }
    }
}

@Composable
fun MovieCastWidget(cast: Cast) {
    Box(modifier = Modifier
        .width(100.dp)
        .wrapContentHeight()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Image(
                painter = rememberImagePainter(data = "${BuildConfig.IMAGE_API_ROOT}w500${cast.profilePath}"),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
            Text(
                text = cast.name ?: stringResource(id = R.string.string_unknown),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}